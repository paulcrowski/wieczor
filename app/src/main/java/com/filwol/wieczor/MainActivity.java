package com.filwol.wieczor;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.view.WindowInsets;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class MainActivity extends Activity {
    private static final int REQ_SAVE = 11;
    private static final int REQ_PICK = 12;

    private WebView web;
    private String pendingSaveContent;
    private ValueCallback<Uri[]> fileCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        web = new WebView(this);
        FrameLayout root = new FrameLayout(this);
        root.addView(web, new FrameLayout.LayoutParams(-1, -1));
        setContentView(root);
        // Android 15 rysuje aplikację pod paskami systemu: odsuwamy treść od paska stanu,
        // paska nawigacji i klawiatury.
        if (Build.VERSION.SDK_INT >= 30) {
            root.setOnApplyWindowInsetsListener((v, insets) -> {
                android.graphics.Insets i = insets.getInsets(
                        WindowInsets.Type.systemBars() | WindowInsets.Type.ime() | WindowInsets.Type.displayCutout());
                v.setPadding(i.left, i.top, i.right, i.bottom);
                return WindowInsets.CONSUMED;
            });
        }

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setAllowFileAccess(true);
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView v, ValueCallback<Uri[]> cb, FileChooserParams p) {
                if (fileCallback != null) fileCallback.onReceiveValue(null);
                fileCallback = cb;
                Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(i, REQ_PICK);
                return true;
            }
        });
        web.addJavascriptInterface(new Bridge(), "Android");
        web.loadUrl("file:///android_asset/index.html#" + tabFor(getIntent()));
    }

    private String tabFor(Intent i) {
        if (i != null && "com.filwol.wieczor.THOUGHT".equals(i.getAction())) return "mysl";
        return "wieczor";
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        web.evaluateJavascript("window.openTab && openTab('" + tabFor(intent) + "')", null);
    }

    @Override
    public void onBackPressed() {
        web.evaluateJavascript("window.handleBack ? handleBack() : false", value -> {
            if (!"true".equals(value)) MainActivity.super.onBackPressed();
        });
    }

    @Override
    protected void onActivityResult(int req, int res, Intent data) {
        super.onActivityResult(req, res, data);
        if (req == REQ_PICK) {
            if (fileCallback != null) {
                Uri u = (res == RESULT_OK && data != null) ? data.getData() : null;
                fileCallback.onReceiveValue(u == null ? null : new Uri[]{u});
                fileCallback = null;
            }
        } else if (req == REQ_SAVE) {
            if (res == RESULT_OK && data != null && data.getData() != null && pendingSaveContent != null) {
                try (OutputStream os = getContentResolver().openOutputStream(data.getData())) {
                    os.write(pendingSaveContent.getBytes(StandardCharsets.UTF_8));
                    Toast.makeText(this, "Zapisano plik", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Nie udało się zapisać", Toast.LENGTH_LONG).show();
                }
            }
            pendingSaveContent = null;
        }
    }

    private File dataFile() { return new File(getFilesDir(), "data.json"); }

    class Bridge {
        @JavascriptInterface
        public String load() {
            try {
                File f = dataFile();
                if (!f.exists()) return "";
                return new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
            } catch (Exception e) { return ""; }
        }

        @JavascriptInterface
        public boolean save(String json) {
            try {
                File tmp = new File(getFilesDir(), "data.json.tmp");
                try (FileOutputStream os = new FileOutputStream(tmp)) {
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                    os.getFD().sync();
                }
                File f = dataFile();
                if (f.exists()) {
                    File bak = new File(getFilesDir(), "data.json.bak");
                    bak.delete();
                    f.renameTo(bak);
                }
                return tmp.renameTo(f);
            } catch (Exception e) { return false; }
        }

        @JavascriptInterface
        public void share(String filename, String content, String mime) {
            runOnUiThread(() -> {
                try {
                    File dir = new File(getCacheDir(), "export");
                    dir.mkdirs();
                    File out = new File(dir, filename);
                    try (FileOutputStream os = new FileOutputStream(out)) {
                        os.write(content.getBytes(StandardCharsets.UTF_8));
                    }
                    Uri uri = FileProvider.getUriForFile(MainActivity.this, "com.filwol.wieczor.files", out);
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType(mime);
                    i.putExtra(Intent.EXTRA_STREAM, uri);
                    i.putExtra(Intent.EXTRA_SUBJECT, filename);
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(i, "Wyślij eksport"));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Nie udało się udostępnić", Toast.LENGTH_LONG).show();
                }
            });
        }

        @JavascriptInterface
        public void saveAs(String filename, String content, String mime) {
            runOnUiThread(() -> {
                pendingSaveContent = content;
                Intent i = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType(mime);
                i.putExtra(Intent.EXTRA_TITLE, filename);
                startActivityForResult(i, REQ_SAVE);
            });
        }

        @JavascriptInterface
        public void setReminder(boolean on, int hour, int minute) {
            Reminders.save(MainActivity.this, on, hour, minute);
            if (on && Build.VERSION.SDK_INT >= 33 &&
                    checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                runOnUiThread(() -> requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 5));
            }
        }

        @JavascriptInterface
        public void toast(String msg) {
            runOnUiThread(() -> Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show());
        }
    }
}
