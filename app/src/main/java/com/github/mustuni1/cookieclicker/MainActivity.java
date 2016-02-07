package com.github.mustuni1.cookieclicker;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.List;
import com.github.mustuni1.cookieclicker.Book;
import com.github.mustuni1.cookieclicker.MySQLiteHelper;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends ActionBarActivity {

    final String PREFS_NAME = "MyPrefsFile";

    final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
    private BroadcastReceiver mReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (settings.getBoolean("first_time", true)) {

            Log.d("Comments", "INITIAL APP LOAD");
            settings.edit().putBoolean("first_time", false).commit();

            settings.edit().putLong("time", Calendar.getInstance().getTimeInMillis()).commit();
            settings.edit().putInt("score", 0).commit();
            settings.edit().putBoolean("on", true).commit();

        }

        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);

    }

    @Override
    protected void onPause() {
        if (ScreenReceiver.wasScreenOn) {}
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ScreenReceiver.wasScreenOn) {}
    }

    @Override
    protected void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
