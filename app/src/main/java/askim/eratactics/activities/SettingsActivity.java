package askim.eratactics.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import askim.eratactics.R;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    public static final String PREFS_NAME = "et_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        // Code from http://stackoverflow.com/questions/5734721/android-shared-preferences
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();

        // Toggle button http://developer.android.com/guide/topics/ui/controls/togglebutton.html
        ToggleButton musicToggle = (ToggleButton) findViewById(R.id.musicToggle);
        musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("music", true);
                } else {
                    editor.putBoolean("music", false);
                }
            }
        });

        ToggleButton sfxToggle = (ToggleButton) findViewById(R.id.vibrationToggle);
        sfxToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("sfx", true);
                } else {
                    editor.putBoolean("sfx", false);
                }
            }
        });

        ToggleButton vibrationToggle = (ToggleButton) findViewById(R.id.vibrationToggle);
        vibrationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("vibration", true);
                } else {
                    editor.putBoolean("vibration", false);
                }
            }
        });

        ToggleButton leftToggle = (ToggleButton) findViewById(R.id.vibrationToggle);
        leftToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putBoolean("leftMode", true);
                } else {
                    editor.putBoolean("leftMode", false);
                }
            }
        });
        editor.apply();
        // Change to commit if we will be changing music settings from other portions of the game
//        editor.commit();

        //code for getting shared preferences
        settings = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);

        Boolean toggle = settings.getBoolean("music", true);
        musicToggle.setChecked(toggle);

        toggle = settings.getBoolean("sfx", true);
        sfxToggle.setChecked(toggle);

        toggle = settings.getBoolean("vibration", false);
        vibrationToggle.setChecked(toggle);

        toggle = settings.getBoolean("leftMode", false);
        leftToggle.setChecked(toggle);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();
    }
}
