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
    private static final String PREFS_NAME = "et_prefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        // Code from http://stackoverflow.com/questions/5734721/android-shared-preferences
        settings = getSharedPreferences(PREFS_NAME, 0);
        editor = settings.edit();
        
        // Toggle button http://developer.android.com/guide/topics/ui/controls/togglebutton.html
        ToggleButton vibrationToggle = (ToggleButton) findViewById(R.id.vibrationToggle);
        vibrationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    editor.putBoolean("vibration", true);
                } else {
                    // The toggle is disabled
                    editor.putBoolean("vibration", false);
                }
            }
        });

        editor.commit();

        //code for getting shared preferences
        settings = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        vibrationToggle = (ToggleButton) findViewById(R.id.vibrationToggle);
        settings.getBoolean("vibration", false);
        // username = (TextView) findViewById(R.id.username);
        // String uname = settings.getString("user_name", null);
        // username.setText(uname);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

//        vibration = (Button) F
    }

}
