package com.github.mustuni1.cookieclicker;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Calendar;

public class ScreenReceiver extends BroadcastReceiver {

    public static boolean wasScreenOn = true;
    final String PREFS_NAME = "MyPrefsFile";

    @Override
    public void onReceive(final Context context, final Intent intent) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("MYAPP", "SCREEN TURNED OFF");

            settings.edit().putLong("time", Calendar.getInstance().getTimeInMillis()).commit();
            wasScreenOn = false;

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.e("MYAPP", "SCREEN TURNED ON");

            long currentTime = Calendar.getInstance().getTimeInMillis();
            long diff = currentTime - settings.getLong("time", 0);
            int intDiff = (int) (long) diff;

            int newScore = settings.getInt("score", 0) + intDiff / 1200;
            settings.edit().putInt("score", newScore).commit();

            settings.edit().putInt("timeSaved", settings.getInt("timeSaved", 0) + intDiff / 6000).commit();

            Log.e("time", currentTime + " : " + settings.getLong("time", 123));
            Log.e("intdiff", intDiff + "");
            Log.e("New score", newScore + "");

            wasScreenOn = true;
        }
    }

}