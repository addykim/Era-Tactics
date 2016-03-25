package askim.eratactics.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import askim.eratactics.R;
import askim.eratactics.gamelogic.Board;
import askim.eratactics.views.BoardView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TacticsGame extends AppCompatActivity {

    private static final String TAG = "Tactics";

    private BoardView mBoard;
    private ImageView firstSkill;
    private ImageView secondSkill;
    private ImageView thirdSkill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tactics_game);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        mBoard = (BoardView) findViewById(R.id.board);
        // Listen for touches on the board
        mBoard.setOnTouchListener(mTouchListener);

        // Setup click listener for each skill buttons
        firstSkill = (ImageView) findViewById(R.id.firstSkill);
        firstSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Log.d(TAG, "First skill clicked");
            }
        });
        secondSkill = (ImageView) findViewById(R.id.secondSkill);
        secondSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Log.d(TAG, "Second skill clicked");
            }
        });
        thirdSkill = (ImageView) findViewById(R.id.thirdSkill);
        thirdSkill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Log.d(TAG, "Third skill clicked");
            }
        });
    }

    // Listen for touches on the board
    // Adapted from in class code
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

        /* Rows 1-3 are for the enemies, 4-6 are for the player */
        public boolean onTouch(View v, MotionEvent event) {
            // Determine which cell was touched
            int col = (int) event.getX() / mBoard.getBoardCellWidth();
            int row = (int) event.getY() / mBoard.getBoardCellHeight();
            Log.d(TAG, "Clicked on column " + (col+1) + " and row " + (row+1));
            return false;

        }

    };
}
