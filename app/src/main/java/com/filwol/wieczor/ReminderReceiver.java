package com.filwol.wieczor;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context c, Intent intent) {
        String a = intent.getAction();
        if ("com.filwol.wieczor.REMIND".equals(a)) {
            NotificationManager nm = c.getSystemService(NotificationManager.class);
            NotificationChannel ch = new NotificationChannel("evening", "Wieczorne pytania", NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(ch);
            Intent open = new Intent(c, MainActivity.class).setAction("com.filwol.wieczor.EVENING")
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pi = PendingIntent.getActivity(c, 2, open,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            Notification n = new Notification.Builder(c, "evening")
                    .setSmallIcon(R.drawable.ic_shortcut_evening)
                    .setContentTitle("Wieczór · 2 minuty")
                    .setContentText("Chciałem, nie zrobiłem · Żółty · Poza ścieżką")
                    .setContentIntent(pi)
                    .setAutoCancel(true)
                    .build();
            try { nm.notify(1, n); } catch (SecurityException ignored) {}
        }
        // Po każdym przypomnieniu i po restarcie telefonu: zaplanuj następne.
        Reminders.schedule(c);
    }
}
