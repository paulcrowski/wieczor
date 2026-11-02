package com.filwol.wieczor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

final class Reminders {
    private Reminders() {}

    static SharedPreferences prefs(Context c) { return c.getSharedPreferences("reminder", Context.MODE_PRIVATE); }

    static void save(Context c, boolean on, int h, int m) {
        prefs(c).edit().putBoolean("on", on).putInt("h", h).putInt("m", m).apply();
        schedule(c);
    }

    static PendingIntent pending(Context c) {
        Intent i = new Intent(c, ReminderReceiver.class).setAction("com.filwol.wieczor.REMIND");
        return PendingIntent.getBroadcast(c, 1, i, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }

    static void schedule(Context c) {
        AlarmManager am = (AlarmManager) c.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = pending(c);
        am.cancel(pi);
        SharedPreferences p = prefs(c);
        if (!p.getBoolean("on", false)) return;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, p.getInt("h", 21));
        cal.set(Calendar.MINUTE, p.getInt("m", 30));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (cal.getTimeInMillis() <= System.currentTimeMillis()) cal.add(Calendar.DAY_OF_MONTH, 1);
        am.setWindow(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), 10 * 60 * 1000L, pi);
    }
}
