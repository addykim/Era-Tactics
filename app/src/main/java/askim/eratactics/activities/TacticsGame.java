package askim.eratactics.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import askim.eratactics.R;
import askim.eratactics.gamelogic.Board;
import askim.eratactics.gamelogic.Team;
import askim.eratactics.views.BoardView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TacticsGame extends AppCompatActivity {

    private static final String TAG = "Tactics";

    /* Draws the board based on boardLogic */
    private BoardView mBoard;

    /* Actual game logic related to the board */
    private Board boardLogic;

    /* True when it is player's turn, false when it is computer's turn */
    private boolean playersTurn;

    /* Returns integer 0-2 inclusive indicating what state the player is in during the game
     * 0 = Selecting a character
     * 1 = Selecting a skill
     * 2 = Selecting a target
     */
    private int turnStatus;

    private int selectedCharacter;
    private int selectedTarget;


    private int firstSkill;
    private ImageView firstSkillButton;

    private int secondSkill;
    private ImageView secondSkillButton;

    private int thirdSkill;
    private ImageView thirdSkillButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tactics_game);

        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();


        //if savestate exists
        //else
        newGame();

        mBoard = (BoardView) findViewById(R.id.board);
        // Listen for touches on the board
        mBoard.setOnTouchListener(mTouchListener);

        // Setup click listener for each skill buttons
        firstSkillButton = (ImageView) findViewById(R.id.firstSkill);
        firstSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                checkSkillTime();
                Log.d(TAG, "First skill clicked");
            }
        });
        secondSkillButton = (ImageView) findViewById(R.id.secondSkill);
        secondSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // TODO
                checkSkillTime();
                Log.d(TAG, "Second skill clicked");
            }
        });
        thirdSkillButton = (ImageView) findViewById(R.id.thirdSkill);
        thirdSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                checkSkillTime();
                Log.d(TAG, "Third skill clicked");
            }
        });
    }

    /* Creates a new game */
    private void newGame() {
        Log.d(TAG, "Creating new game");
        playersTurn = true;
        boardLogic = new Board(new Team());
        turnStatus = 0;
        selectedCharacter = 0;
        selectedTarget = 0;
    }

    /* Switch player turn to computer turn or vice versa */
    private void changeTurn() {
        if (playersTurn) {
            Log.d(TAG, "It is now the computer turn");
            playersTurn = false;
        } else {
            Log.d(TAG, "It is now the player's turn");
            playersTurn = true;
            turnStatus = 0;
        }
        /* Dead pieces are also removed in checkGameOver */
//        checkGameOver();
//      TODO make graphical change to board
//        TODO call reset turn
    }


    /* Changes the available skill icon based on available characters */
    private void displaySkills(int row, int col) {
        boardLogic.getAdventurerSkills(row, col);
//      TODO based on the ArrayList<EnumFile.SkillsEnum> returned, iterate through the ImageViews to change the source
//        firstSkillButton.setImageResource(R.drawable.new_image);
//        firstSkill =
//        secondSkillButton.setImageResource(R.drawable.new_image);
//        secondSkill =
//        thirdSkillButton.setImageResource(R.drawable.new_image);
//        thirdSKill =
    }

    /* Check player's turn and time for skill
     * Increment turnStatus to skill so prevent going back */
    private void checkSkillTime() {
        if (playersTurn && turnStatus == 0) {
            turnStatus++;
//            TODO call available targets passing the skill enum
        }
    }

    // Listen for touches on the board
    // Adapted from in class code
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {

        /* Rows 1-3 are for the enemies, 4-6 are for the player */
        public boolean onTouch(View v, MotionEvent event) {
            // Determine which cell was touched
            int row = (int) event.getY() / mBoard.getBoardCellHeight();
            int col = (int) event.getX() / mBoard.getBoardCellWidth();
            String location = "column " + (col+1) + " and row " + (row+1);
//            TODO what if -1 is returned from resolve grid
            if (boardLogic.resolveGrid(row, col) == 2) {
                Log.d(TAG, "Selected adventurer at " + location);
                /* Selecting a character */
                if (turnStatus == 0) {
                    //TODO current character
                /* If it is time to select a target */
                } else if (turnStatus == 2) {
                    //TODO validate selected appropriate target
                    // if valid target {
                    // call resolve skill
                    // update any graphics, redraw board?
                    // changeTurn();
                    // }else do nothing
                }
            }
            Log.d(TAG, "Clicked on column " + (col+1) + " and row " + (row+1));
            return false;
        }
    };
}
