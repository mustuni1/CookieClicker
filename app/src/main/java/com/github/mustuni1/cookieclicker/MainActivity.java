package com.github.mustuni1.cookieclicker;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.github.mustuni1.cookieclicker.Book;
import com.github.mustuni1.cookieclicker.MySQLiteHelper;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

    SharedPreferences settings;

    TextView condition, totalmin, totalcoins,reward;
    EditText inpminutes;
    Button start, rules, shop;
    AlertDialog alertDialog;

    public void addListenerOnButton() {
        final Context context = this;

        rules.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // The code that does log and shows rules which we will make

                alertDialog.show();
            }

        });
        shop.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // The code that switches to shop activity
                Intent intent = new Intent(context, ShopActivity.class);
                startActivity(intent);

            }

        });
    }

    final String PREFS_NAME = "MyPrefsFile";

    final IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
    private BroadcastReceiver mReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = getApplicationContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        if (settings.getBoolean("first_time", true)) {

            Log.d("Comments", "INITIAL APP LOAD");
            settings.edit().putBoolean("first_time", false).commit();

            settings.edit().putLong("time", Calendar.getInstance().getTimeInMillis()).commit();
            settings.edit().putInt("score", 0).commit();
            settings.edit().putBoolean("on", true).commit();
            settings.edit().putInt("timeSaved", 0).commit();
            Set<String> stringSet = new HashSet<String>();
            stringSet.add("1");
            stringSet.add("25");
            stringSet.add("17");
            stringSet.add("43");
            settings.edit().putStringSet("items", stringSet);

        }

        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);

        setContentView(R.layout.activity_main);
        totalmin =  (TextView) findViewById(R.id.lblTotalMinutes);
        totalcoins = (TextView) findViewById(R.id.lblTotalCoins);
        rules = (Button) findViewById(R.id.btnRules);
        shop = (Button) findViewById(R.id.btnShop);
        //check if timer is over :)
        addListenerOnButton();
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Help");
        alertDialog.setMessage(" You got this game to stop wasting your life on an electric brick in a fun way. Well, the magic in this game is it gives you an incentive for not using your phone in money $$$. 100 hack coins is used to spin the Collector, and you get items. Try collecting all these! Every minute you get 3 coins and every 60 minutes you get 50 extra coins.");
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
// here you can add functions
            }
        });

    }

    @Override
    protected void onPause() {
        if (ScreenReceiver.wasScreenOn) {}
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        final TextView textViewToChange = (TextView) findViewById(R.id.lblTotalCoins);
        textViewToChange.setText(settings.getInt("score", 0) + "");

        final TextView updateSavedMinutes = (TextView) findViewById(R.id.lblTotalMinutes);
        updateSavedMinutes.setText(settings.getInt("timeSaved", 0)+"");

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
