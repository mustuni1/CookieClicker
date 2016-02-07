package com.github.mustuni1.cookieclicker;

/**
 * Created by test on 2/7/16.
 */

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class ShopActivity extends ActionBarActivity{

    /**
     * Created by nithin on 2/6/16.
     */
    private Button clickButton;
    private Timer timer;
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run () {
            if(this == null)
                return;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                // get random image and change existing image's link
                    final Random random = new Random();
                    int num = random.nextInt(45);
                    Log.e("generated num", num + "");
                    ImageView myImgView = (ImageView) findViewById(R.id.imageView);
                    String uri = "@drawable/img_" + num;

                    int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                    myImgView.setImageResource(imageResource);
                }
            });
        }
    };

    public void start() {
        if(timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 150);
    }

    public void stop() {
        timer.cancel();
        timer = null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        //check if timer is over :)

        start();
    }

    @Override
    protected void onPause() {
        if (ScreenReceiver.wasScreenOn) {}
        super.onPause();
        stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        start();
    }

    public void onBtnClicked(View v){
        if(v.getId() == R.id.button){
            stop();
        }
    }
}
