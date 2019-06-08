package com.james.chargescreen;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.BatteryManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.skyfishjy.library.RippleBackground;

public class ChargeActivity extends Activity {

    boolean isDark;
    int backgroundColor, progressBarColor, systemBarColor;

    TextView tv;
    ProgressBar progressBar;
    RippleBackground rippleBackground;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        isDark = prefs.getBoolean("isDark", false);

        if (prefs.getBoolean("isFullScreen", false)) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_ripples);

        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        rippleBackground = (RippleBackground)findViewById(R.id.content);
        tv = (TextView) findViewById(R.id.chargepercent);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        rippleBackground.startRippleAnimation();

        backgroundColor = prefs.getInt("backgroundColor", ContextCompat.getColor(this, R.color.light));
        progressBarColor = prefs.getInt("progressBarColor", ContextCompat.getColor(this, R.color.teal));
        systemBarColor = prefs.getInt("systemBarColor", ContextCompat.getColor(this, R.color.lightblu));

        ((ImageView) findViewById(R.id.backgroundImage)).setImageDrawable(WallpaperManager.getInstance(this).getDrawable());
        ((ImageView) findViewById(R.id.backgroundTint)).setImageDrawable(new ColorDrawable(backgroundColor));

        progressBar.getProgressDrawable().setColorFilter(progressBarColor, PorterDuff.Mode.SRC_IN);

        Intent batteryIntent = this.getApplicationContext().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int rawlevel = batteryIntent.getIntExtra("level", -1);
        double scale = batteryIntent.getIntExtra("scale", -1);
        double level = -1;
        if (rawlevel >= 0 && scale > 0) {
            level = rawlevel / scale;
            String per = String.valueOf(level);
            per = per.replace("0.", "");
            if(per.matches("1.0")){
                per = "100";
            }
            tv.setText("Charging: " + per + "%");
            int progress = Integer.parseInt(per);
            progressBar.setProgress(progress);
        }

        if (isColorDark(backgroundColor)){
            tv.setTextColor(Color.WHITE);
        } else {
            tv.setTextColor(Color.BLACK);
        }

        if(isDark){
            WindowManager.LayoutParams layout = getWindow().getAttributes();
            layout.screenBrightness = 0F;
            getWindow().setAttributes(layout);
        }

        if(android.os.Build.VERSION.SDK_INT >= 21){
            getWindow().setStatusBarColor(systemBarColor);
            getWindow().setNavigationBarColor(systemBarColor);
        }

        FrameLayout fl = (FrameLayout) findViewById(R.id.fl);
        fl.setBackgroundColor(backgroundColor);
    }

    boolean isColorDark(int color){
        double darkness = 1-(0.299* Color.red(color) + 0.587*Color.green(color) + 0.114* Color.blue(color))/255;
        return darkness >= 0.5;
    }

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            tv.setText("Charging: " + String.valueOf(level) + "%");
            progressBar.setProgress(level);
        }
    };

}
