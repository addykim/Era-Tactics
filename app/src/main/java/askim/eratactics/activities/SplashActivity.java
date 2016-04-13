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
                Intent intent = new Intent(getApplicationContext(), TacticsGame.class);
                Log.d(TAG, "Adventure button clicked");
                startActivity(intent);
            }
        });
        membersButton = (Button) findViewById(R.id.membersButton);
        membersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MembersActivity.class);
                Log.d(TAG, "Members button clicked");
                startActivity(intent);
            }
        });
        teamsButton = (Button) findViewById(R.id.teamsButton);
        teamsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TeamsActivity.class);
                Log.d(TAG, "Teams button clicked");
                startActivity(intent);
            }
        });
        settingsButton = (Button) findViewById(R.id.settingsButton);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                Log.d(TAG, "SettingsActivity button clicked");
                startActivity(intent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
