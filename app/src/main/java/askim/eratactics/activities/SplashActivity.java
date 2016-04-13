package askim.eratactics.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import askim.eratactics.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "Splash";
    private Button adventureButton;
    private Button membersButton;
    private Button teamsButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        adventureButton = (Button) findViewById(R.id.adventureButton);
        adventureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TacticsGame.class);
                startActivity(intent);
                Log.d(TAG, "Adventure button clicked");
            }
        });
        membersButton = (Button) findViewById(R.id.membersButton);
        membersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Members is not available in this version", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Members button clicked");
//                TODO start members intent
            }
        });
        teamsButton = (Button) findViewById(R.id.teamsButton);
        teamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Teams Customization is not available in this version", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Teams button clicked");
//                TODO start team intent
            }
        });
        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "SettingsActivity is not available in this version", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), SettingsActivity.class);
//                startActivity(intent);
                Log.d(TAG, "SettingsActivity button clicked");
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
