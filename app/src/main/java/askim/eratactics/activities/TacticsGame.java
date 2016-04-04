package askim.eratactics.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import askim.eratactics.R;
import askim.eratactics.gamelogic.Adventurer;
import askim.eratactics.gamelogic.Board;
import askim.eratactics.gamelogic.EnumFile;
import askim.eratactics.gamelogic.Equipment;
import askim.eratactics.gamelogic.Team;
import askim.eratactics.views.BoardView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TacticsGame extends AppCompatActivity {


    private BackgroundSound mBackgroundSound;

    private static final String TAG = "TacticsGame";

    private static final float OPAQUE = 1;
    private static final float HIGHLIGHT = (float) 0.75;


    /* Draws the board based on boardLogic */
    private BoardView boardView;

    /* Actual game logic related to the board */
    private Board boardLogic;

    /* True when it is player's turn, false when it is computer's turn */
    private boolean playersTurn;

    private EnumFile.TurnStatus turnStatus;

    private int selectedChar;
    private int selectedCharRow;
    private int selectedCharCol;
    private EnumFile.SkillsEnum selectedSkill;
    private ArrayList<Integer> possibleTargets;

    /* Skill buttons */
    private ImageView firstSkillButton;
    private EnumFile.SkillsEnum firstSkill;
    private ImageView secondSkillButton;
    private EnumFile.SkillsEnum secondSkill;
    private ImageView thirdSkillButton;
    private EnumFile.SkillsEnum thirdSkill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tactics_game);

        mBackgroundSound = new BackgroundSound();
        mBackgroundSound.execute();
        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();


        //if savestate exists
        //else
        newGame();
        Team alphaTeam = new Team();
        Adventurer villager1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.VILLAGER)});
        Adventurer apprentice1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.APPRENTICE),
                                                                new Equipment(EnumFile.Equipments.BASIC_POTION)});
        Adventurer magician1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.MAGICIAN),
                                                              new Equipment(EnumFile.Equipments.BASIC_WAND),
                                                              new Equipment(EnumFile.Equipments.BASIC_POTION)});
        Adventurer archer1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.ARCHER),
                                                            new Equipment(EnumFile.Equipments.BASIC_ARROW)});
        alphaTeam.addAdventurer(villager1, 2, false);
        alphaTeam.addAdventurer(apprentice1, 6, false);
        alphaTeam.addAdventurer(magician1, 3, false);
        alphaTeam.addAdventurer(archer1, 5, false);


//        boardLogic = new Board(new Team());
        boardLogic = new Board(alphaTeam);
        boardView = (BoardView) findViewById(R.id.board);
        boardView.setGame(boardLogic);
//        boardLogic.setBoardView(boardView);

        // Listen for touches on the board
        boardView.setOnTouchListener(mTouchListener);

        // Setup click listener for each skill buttons
        firstSkillButton = (ImageView) findViewById(R.id.move);
        firstSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSkill(0);
//                executeSkill(firstSkill);
                executeSkill(EnumFile.SkillsEnum.MOVE);

            }
        });
        secondSkillButton = (ImageView) findViewById(R.id.punch);
        secondSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSkill(1);
                executeSkill(EnumFile.SkillsEnum.PUNCH);
            }
        });
        thirdSkillButton = (ImageView) findViewById(R.id.thirdSkill);
        thirdSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSkill(2);
                // TODO get skill
//                executeSkill("Normal Skill");
//                selectedSkill =

            }
        });
    }

    /* Send in a value 0 through 2, and boolean if we want to highlight or unhighlight */
    private void highlightSkill(int skillNumber) {
        /* Unhighlight everything */
        for (int index = 0; index < 3; index++) {
            firstSkillButton.setAlpha(OPAQUE);
            secondSkillButton.setAlpha(OPAQUE);
            thirdSkillButton.setAlpha(OPAQUE);
        }
        if (skillNumber == 0)
            firstSkillButton.setAlpha(HIGHLIGHT);
        else if (skillNumber == 1)
            secondSkillButton.setAlpha(HIGHLIGHT);
        else if (skillNumber == 2)
            thirdSkillButton.setAlpha(HIGHLIGHT);
        else
            Log.d(TAG, "Un-highlighting all skill");
    }

    /* Execute a skill if a valid character was selected
     * Changes turn status to skill so another character can not be selected */
    public void executeSkill(EnumFile.SkillsEnum skill) {
        if (turnStatus == EnumFile.TurnStatus.CHARACTER && validCharacter()) {
            turnStatus = EnumFile.TurnStatus.SKILL;
            selectedSkill = skill;
            showTargets();
        } else {
            Log.d(TAG, "Can not change turnstatus = skill");
            highlightSkill(-1);
        }
    }

    /* Return true if a piece is valid character */
    private boolean validCharacter() {
        return 0 <= selectedChar && selectedChar <= 17;
    }

    /* Resume music onResume */
    public void onResume() {
        super.onResume();
        if (mBackgroundSound.isCancelled()) {
            mBackgroundSound.execute();
        }
    }

    /* Pause music as needed */
    public void onPause() {
        super.onPause();
        mBackgroundSound.cancel(true);
    }

    /* Creates a new game */
    private void newGame() {
        Log.d(TAG, "Creating new game");
//        TODO replace newBoard(new Team) with clearboard command
//        playersTurn = true;
        resetValues();
        possibleTargets = new ArrayList<Integer>();
    }

    /* Call at new game and every time change turned */
    private void resetValues() {
        turnStatus = EnumFile.TurnStatus.CHARACTER;
        selectedChar = -1;
        selectedCharCol = -1;
        selectedCharRow = -1;
        selectedSkill = EnumFile.SkillsEnum.INVALID;
    }

    /* Switch player turn to computer turn or vice versa */
    private void changeTurn() {
        /* Unhighlight skill */
        highlightSkill(-1);
        // TODO future if the player or computer is out of moves but the other is not, then skip the person who is out of turn until the other is out also
        // If out of active pieces, reset all the pieces and try make move again
        do {
            Log.d(TAG, "It is now the computer turn");
            boardLogic.makeComputerMove();

        } while (boardLogic.getActivePlayers() == 0 && boardLogic.getActiveEnemies() != 0);

        if (boardLogic.getActiveEnemies() == 0 && boardLogic.getActivePlayers() == 0) {
            Log.d(TAG, "Resetting all the piece's turn");
            boardLogic.resetTurn();
            Toast.makeText(getApplicationContext(), "New Round", Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "It is now the player's turn");
        /* Reset everything for the player's turn */
        resetValues();
        boardView.setTargets(null);
        boardView.setCharacter(selectedChar);
        boardView.invalidate();

        /* Dead pieces are also removed in checkGameOver */
        Log.d(TAG, "Checking for winner");
        int result = boardLogic.checkGameOver();
        if (result == 1) {
            //TODO replace with actual intent to win or lose screen
            Intent results = new Intent(this, Result.class);
            results.putExtra("win", true);
            startActivity(results);
            Toast.makeText(getApplicationContext(), "You win!", Toast.LENGTH_LONG).show();
        } else if (result == 2) {
            Intent results = new Intent(this, Result.class);
            results.putExtra("win", false);
            startActivity(results);
            Toast.makeText(getApplicationContext(), "You Lose!", Toast.LENGTH_LONG).show();
        } else if (result == -1) {
            Log.d(TAG, "SOMETHING WENT VERY VERY WRONG AHHHHHHHH");
        }
        // else continue playing
    }

    /* Changes the available skill icon based on available characters */
    private void displaySkills(int row, int col) {
        boardLogic.getAdventurerSkills(row, col);

//      TODO based on the ArrayList<EnumFile.SkillsEnum> returned, iterate through the ImageViews to change the source
//        skillButton.setImageResource(R.drawable.new_image);
//        skillButton =
    }


    /* Circles available targets to use skill on */
    public void showTargets() {
        possibleTargets =
                boardLogic.availableTargets(selectedCharRow, selectedCharCol, selectedSkill);
        boardView.setTargets(possibleTargets);
        boardView.invalidate();
    }

    // Listen for touches on the board
    // Adapted from in class code
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        /* Rows 1-3 are for the enemies, 4-6 are for the player */
        public boolean onTouch(View v, MotionEvent event) {
            // Determine which cell was touched
            int row = (int) event.getY() / boardView.getBoardCellHeight();
            int col = (int) event.getX() / boardView.getBoardCellWidth();
            String log = "Clicked on row " + (row+1) + ", col " + (col+1);
            if (boardLogic.resolveGrid(row, col) == 0) {
                touchedEmpty(row, col, log);
            } else if (boardLogic.resolveGrid(row,col) == 1) {
                touchedEnemy(row, col, log);
            } else if (boardLogic.resolveGrid(row, col) == 2) {
                touchedCharacter(row, col, log);
            } else if (boardLogic.resolveGrid(row, col) == 3) {
                /* Selected inactive enemy */
                Toast.makeText(getApplicationContext(),
                        "You already used this character", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, log + "What");
            }
            return false;
        }
    };

    /* Clicked on empty spot on grid */
    private void touchedEmpty(int row, int col, String log) {
        /* If moving */
        if (turnStatus == EnumFile.TurnStatus.SKILL && selectedSkill == EnumFile.SkillsEnum.MOVE) {
            if (possibleTargets.contains(row*3+col)) {
                Log.d(TAG, log + ", target is valid spot to move to");
                boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row * 3 + col), selectedSkill);
                changeTurn();
            } else {
                Log.d(TAG, log + ", cannot move here");
            }
        }
    }

    /* Selected enemy's character */
    private void touchedEnemy(int row, int col, String log) {
        if (turnStatus == EnumFile.TurnStatus.SKILL && selectedSkill != EnumFile.SkillsEnum.INVALID
                && selectedSkill != EnumFile.SkillsEnum.HEAL && selectedSkill != EnumFile.SkillsEnum.MOVE) {
            Log.d(TAG, log + ", selected computer's character, using skill " + selectedSkill);
            boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row * 3 + col), selectedSkill);
            changeTurn();
        } else {
            Log.d(TAG, log + ", selected computer's character, cannot use skill");
        }
    }

    /* Selected character */
    private void touchedCharacter(int row, int col, String log) {
        /* Selecting a character */
        if (turnStatus == EnumFile.TurnStatus.CHARACTER) {
            log += ", selected player's character";
            selectedCharRow = row;
            selectedCharCol = col;
            selectedChar = row*3+col;
            boardView.setCharacter(selectedChar);
            boardView.invalidate();
        /* If it is time to select a target */
        } else if (turnStatus == EnumFile.TurnStatus.SKILL && selectedSkill != EnumFile.SkillsEnum.INVALID) {
            /* Check if selected adventurer is in the target list */
            if (possibleTargets.contains(row*3+col)) {
                Log.d(TAG, log + ", target is valid to use skill on");
                boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row * 3 + col), selectedSkill);
                changeTurn();
            } else {
                Log.d(TAG, log + ", target is not valid to use skill on");
            }
        }
    }

    /* Code from this stack overflow thread here http://stackoverflow.com/questions/7928803/background-music-android */
    public class BackgroundSound extends AsyncTask<Void, Void, Void> {

        // TODO prepare beforehand

        @Override
        protected Void doInBackground(Void... params) {
            Log.d(TAG, "Starting bgm");
            MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.bgm);
            player.setLooping(true); // Set looping
            player.setVolume(1.0f, 1.0f);
            player.start();
            return null;
        }
    }
}

