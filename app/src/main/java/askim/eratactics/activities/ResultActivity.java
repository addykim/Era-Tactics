package askim.eratactics.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orm.SugarContext;

import askim.eratactics.R;
import askim.eratactics.gamelogic.LevelGenerator;
import askim.eratactics.views.Resources;

public class ResultActivity extends Activity {

    private static final String TAG = "ResultActivity";
    private TextView text;
    private ImageView image;
    private TextView backButton;

    private Long levelPlayed;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//        SugarContext.init(this);

        Intent results = getIntent(); // gets the previously created intent
        levelPlayed = results.getLongExtra("level", 1);
        mPrefs = getSharedPreferences(Resources.PREFS_NAME, MODE_PRIVATE);

        text = (TextView) findViewById(R.id.result_header);
        image = (ImageView) findViewById(R.id.result_image);
        backButton = (TextView) findViewById(R.id.back_to_splash);
        boolean win = results.getBooleanExtra("win", false);
        if (win)
            levelWon();
        else
            levelLost();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }

    private void levelWon() {
        text.setText(R.string.postGameCleared);
        if (mPrefs.getBoolean("vibration", false)) {
            // TODO play winning sound
            LevelGenerator level = LevelGenerator.findById(LevelGenerator.class, levelPlayed);
            try {
                level.setCleared(true);
                level.save();
            } catch (NullPointerException e) {
                Log.d(TAG, "LEVEL IS NULL :(");
            } finally {
                if (level != null) {
                    Log.d(TAG, "Level updated successfully");
                    Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                    v.vibrate(500);
                } else {
                    Log.d(TAG, "Level is null in finally :(");
                }
            }
            /* Set next level as playable */
            LevelGenerator nextLevel = LevelGenerator.findById(LevelGenerator.class, levelPlayed+1);
            try {
                nextLevel.setLocked(false);
                nextLevel.save();
            } catch (NullPointerException e) {
                Log.d(TAG, "NEXT LEVEL IS NULL:(");
            } finally {
                if (nextLevel != null) {
                    Log.d(TAG, "Next level updated successfully");
                } else {
                    Log.d(TAG, "Next level is null :(");
                }
            }
        }
    }

    private void levelLost() {
        // TODO play sad sound :(
        text.setText(R.string.postGameLose);
        image.setImageResource(R.drawable.lose_game);

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        SugarContext.terminate();
//    }

}
