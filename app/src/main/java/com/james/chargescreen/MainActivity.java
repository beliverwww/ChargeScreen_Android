package com.james.chargescreen;

import android.app.WallpaperManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.thoughtbot.stencil.StencilView;

import james.colorpickerdialog.dialogs.ColorPickerDialog;
import james.colorpickerdialog.dialogs.PreferenceDialog;


public class MainActivity extends AppCompatActivity {

    private SwitchCompat enabledSwitch, darkSwitch, fullScreenSwitch;

    private int backgroundColor, progressBarColor, systemBarColor;
    private View background, progressBar, systemBar;
    private ImageView backgroundImage, progressBarImage, systemBarImage;

    private Toolbar toolbar;
    private StencilView stencilView;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prefs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        enabledSwitch = (SwitchCompat) findViewById(R.id.enabledSwitch);
        darkSwitch = (SwitchCompat) findViewById(R.id.darkSwitch);
        fullScreenSwitch = (SwitchCompat) findViewById(R.id.fullScreenSwitch);
        background = findViewById(R.id.backgroundColor);
        progressBar = findViewById(R.id.progressBarColor);
        systemBar = findViewById(R.id.systemBarColor);
        backgroundImage = (ImageView) findViewById(R.id.backgroundColorImage);
        progressBarImage = (ImageView) findViewById(R.id.progressBarImage);
        systemBarImage = (ImageView) findViewById(R.id.systemBarImage);

        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_prefs);

        ((ImageView) findViewById(R.id.backgroundImage)).setImageDrawable(WallpaperManager.getInstance(this).getDrawable());

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        enabledSwitch.setChecked(prefs.getBoolean("isEnabled", false));
        darkSwitch.setChecked(prefs.getBoolean("isDark", false));
        fullScreenSwitch.setChecked(prefs.getBoolean("isFullScreen", false));

        enabledSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean("isEnabled", isChecked).apply();
            }
        });

        darkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean("isDark", isChecked).apply();
            }
        });

        fullScreenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean("isFullScreen", isChecked).apply();
            }
        });

        backgroundColor = prefs.getInt("backgroundColor", ContextCompat.getColor(this, R.color.light));
        progressBarColor = prefs.getInt("progressBarColor", ContextCompat.getColor(this, R.color.teal));
        systemBarColor = prefs.getInt("systemBarColor", ContextCompat.getColor(this, R.color.lightblu));

        backgroundImage.setImageDrawable(new ColorDrawable(backgroundColor));
        progressBarImage.setImageDrawable(new ColorDrawable(progressBarColor));
        progressBarImage.setImageDrawable(new ColorDrawable(progressBarColor));

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog(MainActivity.this).setPreference(backgroundColor).setListener(new PreferenceDialog.OnPreferenceListener<Integer>() {
                    @Override
                    public void onPreference(PreferenceDialog dialog, Integer preference) {
                        prefs.edit().putInt("backgroundColor", preference).apply();
                        backgroundImage.setImageDrawable(new ColorDrawable(preference));
                        backgroundColor = preference;
                    }

                    @Override
                    public void onCancel(PreferenceDialog dialog) {
                    }
                }).show();
            }
        });

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog(MainActivity.this).setPreference(progressBarColor).setListener(new PreferenceDialog.OnPreferenceListener<Integer>() {
                    @Override
                    public void onPreference(PreferenceDialog dialog, Integer preference) {
                        prefs.edit().putInt("progressBarColor", preference).apply();
                        progressBarImage.setImageDrawable(new ColorDrawable(preference));
                        progressBarColor = preference;
                    }

                    @Override
                    public void onCancel(PreferenceDialog dialog) {
                    }
                }).show();
            }
        });

        systemBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ColorPickerDialog(MainActivity.this).setPreference(systemBarColor).setListener(new PreferenceDialog.OnPreferenceListener<Integer>() {
                    @Override
                    public void onPreference(PreferenceDialog dialog, Integer preference) {
                        prefs.edit().putInt("systemBarColor", preference).apply();
                        systemBarImage.setImageDrawable(new ColorDrawable(preference));
                        systemBarColor = preference;
                    }

                    @Override
                    public void onCancel(PreferenceDialog dialog) {
                    }
                }).show();
            }
        });

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundColor = ContextCompat.getColor(MainActivity.this, R.color.light);
                progressBarColor = ContextCompat.getColor(MainActivity.this, R.color.teal);
                systemBarColor = ContextCompat.getColor(MainActivity.this, R.color.lightblu);

                prefs.edit().putInt("backgroundColor", backgroundColor).apply();
                prefs.edit().putInt("progressBarColor", progressBarColor).apply();
                prefs.edit().putInt("systemBarColor", systemBarColor).apply();
                backgroundImage.setImageDrawable(new ColorDrawable(backgroundColor)) ;
                progressBarImage.setImageDrawable(new ColorDrawable(progressBarColor)) ;
                systemBarImage.setImageDrawable(new ColorDrawable(systemBarColor)) ;

                Toast.makeText(MainActivity.this, "Reset", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.previewButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ChargeActivity.class));
            }
        });

        if(!prefs.getBoolean("tutorial", false)){
            startActivity(new Intent(MainActivity.this, IntroActivity.class));
            prefs.edit().putBoolean("tutorial", true).apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prefs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_tutorial:
                startActivity(new Intent(MainActivity.this, IntroActivity.class));
                break;
            case R.id.action_about:

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
