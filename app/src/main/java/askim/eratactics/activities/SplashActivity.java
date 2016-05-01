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

import com.orm.SugarContext;

import java.lang.reflect.Member;
import java.util.logging.Level;

import askim.eratactics.R;
import askim.eratactics.gamelogic.Adventurer;
import askim.eratactics.gamelogic.Equipment;
import askim.eratactics.gamelogic.LevelGenerator;
import askim.eratactics.gamelogic.PlayerAdventurers;
import askim.eratactics.gamelogic.Team;

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

    private Button inventoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        SugarContext.init(this);

        //TODO sharedpreferences
        Adventurer.deleteAll(Adventurer.class);
        Team.deleteAll(Team.class);
        Equipment.deleteAll(Equipment.class);
        LevelGenerator.deleteAll(LevelGenerator.class);

        PlayerAdventurers members = new PlayerAdventurers(this);
        for (int i = 1; i < 5; i++) {
            LevelGenerator level = new LevelGenerator(this, i);
        }

        adventureButton = (Button) findViewById(R.id.adventureButton);
        adventureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LevelSelectActivity.class);
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
        inventoryButton = (Button) findViewById(R.id.tempTeamsButton);
        inventoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InventoryActivity.class);

                Log.d(TAG, "Teams button clicked");
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
    }
}
