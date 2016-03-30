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

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

//        vibration = (Button) F
    }

}
