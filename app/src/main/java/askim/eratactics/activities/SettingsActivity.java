package askim.eratactics.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import askim.eratactics.R;
import askim.eratactics.views.Resources;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private static final String TAG = "Settings";

    // Toggles
    private ToggleButton musicToggle;
    private ToggleButton vibrationToggle;
    private ToggleButton sfxToggle;
    private ToggleButton leftToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        // Code from http://stackoverflow.com/questions/5734721/android-shared-preferences
        settings = getSharedPreferences(Resources.PREFS_NAME, Activity.MODE_PRIVATE);
        musicToggle = (ToggleButton) findViewById(R.id.musicToggle);
        sfxToggle = (ToggleButton) findViewById(R.id.sfxToggle);
        vibrationToggle = (ToggleButton) findViewById(R.id.vibrationToggle);
        leftToggle = (ToggleButton) findViewById(R.id.leftHandedToggle);

        // Set checked
        musicToggle.setChecked(settings.getBoolean("music", true));
        sfxToggle.setChecked(settings.getBoolean("sfx", true));
        vibrationToggle.setChecked(settings.getBoolean("vibration", false));
        leftToggle.setChecked(settings.getBoolean("leftMode", false));


        editor = settings.edit();

        // Toggle button http://developer.android.com/guide/topics/ui/controls/togglebutton.html
        musicToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "Music toggle: " + isChecked);
                if (isChecked) {
                    editor.putBoolean("music", true);
                } else {
                    editor.putBoolean("music", false);
                }
                Log.d(TAG, "Commiting");
                editor.commit();
            }
        });

        sfxToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "SFX toggle: " + isChecked);
                if (isChecked) {
                    editor.putBoolean("sfx", true);
                } else {
                    editor.putBoolean("sfx", false);
                }
                Log.d(TAG, "Commiting");
                editor.commit();
            }
        });

        vibrationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "Vibration toggle: " + isChecked);
                if (isChecked) {
                    editor.putBoolean("vibration", true);
                } else {
                    editor.putBoolean("vibration", false);
                }
                Log.d(TAG, "Commiting");
                editor.commit();
            }
        });

        leftToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "left toggle: " + isChecked);
                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "Sorry left handed mode isn't available yet :(", Toast.LENGTH_LONG).show();
                    editor.putBoolean("leftMode", true);
                } else {
                    editor.putBoolean("leftMode", false);
                }
                Log.d(TAG, "Commiting");
                editor.commit();
            }
        });
    }
}


