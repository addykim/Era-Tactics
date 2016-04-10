package askim.eratactics.activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import askim.eratactics.R;

public class Settings extends AppCompatActivity {

//    TODO these may not be buttons
//    private Button
    private Button vibration;
    private Button rHandMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        
        // Code from http://stackoverflow.com/questions/5734721/android-shared-preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        
        // Toggle button http://developer.android.com/guide/topics/ui/controls/togglebutton.html
        ToggleButton vibrationToggle = (ToggleButton) findViewById(R.id.vibrationToggle);
        vibrationToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    editor.putBoolean("vibration"), true);
                } else {
                    // The toggle is disabled
                    editor.putBoolean("vibration", false);
                }
            }
        });

        editor.commit();

        //code for getting shared preferences
        SharedPreferences settings = getSharedPreferences(SignIn.PREFS_NAME,
                Activity.MODE_PRIVATE);
        // username = (TextView) findViewById(R.id.username);
        // String uname = settings.getString("user_name", null);
        // username.setText(uname);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

//        vibration = (Button) F
    }

}
