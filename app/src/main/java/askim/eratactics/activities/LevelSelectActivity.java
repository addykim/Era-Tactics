package askim.eratactics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import askim.eratactics.R;

public class LevelSelectActivity extends AppCompatActivity {

    private static final String TAG = "Level select";


     // TODO replace with a level select button views
    private Button firstLevel;
    private Button secondLevel;
    private Button thirdLevel;
    private Button forthLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);

        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();


        // TODO programmatically create list of levels
        // TODO hook up buttons so they select the various levels
        firstLevel = (Button) findViewById(R.id.firstLevel);
        firstLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TacticsGame.class);
                Log.d(TAG, "First level button clicked");
                startActivity(intent);
            }
        });

        secondLevel = (Button) findViewById(R.id.secondLevel);
        secondLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TacticsGame.class);
                Log.d(TAG, "First level button clicked");
                startActivity(intent);
            }
        });

        thirdLevel = (Button) findViewById(R.id.thirdLevel);
        thirdLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TacticsGame.class);
                Log.d(TAG, "First level button clicked");
                startActivity(intent);
            }
        });

        forthLevel = (Button) findViewById(R.id.forthLevel);
        forthLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TacticsGame.class);
                Log.d(TAG, "First level button clicked");
                startActivity(intent);
            }
        });
    }

}
