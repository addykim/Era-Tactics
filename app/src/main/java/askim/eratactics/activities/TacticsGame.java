package askim.eratactics.activities;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
    private ImageView fourthSkillButton;
    private EnumFile.SkillsEnum fourthSkill;

    private Handler mPauseHandler;
    private Runnable myRunnable;


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

        mPauseHandler = new Handler();

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

        // Listen for touches on the board
        boardView.setOnTouchListener(mTouchListener);

        // Setup click listener for each skill buttons
        firstSkillButton = (ImageView) findViewById(R.id.firstSkill);
        firstSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSkill(0);
                executeSkill(firstSkill);

            }
        });
        secondSkillButton = (ImageView) findViewById(R.id.secondSkill);
        secondSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSkill(1);
                executeSkill(secondSkill);
            }
        });
        thirdSkillButton = (ImageView) findViewById(R.id.thirdSkill);
        thirdSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSkill(2);
                executeSkill(thirdSkill);
            }
        });

        fourthSkillButton = (ImageView) findViewById(R.id.fourthSkill);
        fourthSkillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                highlightSkill(3);
                executeSkill(fourthSkill);
            }
        });

    }

    private void resetSkills() {
        firstSkillButton.setImageResource(0);
        secondSkillButton.setImageResource(0);
        thirdSkillButton.setImageResource(0);
        fourthSkillButton.setImageResource(0);

    }

    /* Send in a value 0 through 2, and boolean if we want to highlight or unhighlight */
    private void highlightSkill(int skillNumber) {
        /* Unhighlight everything */
        for (int index = 0; index < 3; index++) {
            firstSkillButton.setAlpha(OPAQUE);
            secondSkillButton.setAlpha(OPAQUE);
            thirdSkillButton.setAlpha(OPAQUE);
            fourthSkillButton.setAlpha(OPAQUE);
        }
        if (skillNumber == 0)
            firstSkillButton.setAlpha(HIGHLIGHT);
        else if (skillNumber == 1)
            secondSkillButton.setAlpha(HIGHLIGHT);
        else if (skillNumber == 2)
            thirdSkillButton.setAlpha(HIGHLIGHT);
        else if (skillNumber == 3)
            fourthSkillButton.setAlpha(HIGHLIGHT);
        else
            Log.d(TAG, "Un-highlighting all skill");
    }

    /* Execute a skill if a valid character was selected
     * Changes turn status to skill so another character can not be selected */
    public void executeSkill(EnumFile.SkillsEnum skill) {
        if (validCharacter()) {
            if (turnStatus == EnumFile.TurnStatus.CHARACTER) {
                turnStatus = EnumFile.TurnStatus.SKILL;
                selectedSkill = skill;
                showTargets();
            } else if (turnStatus == EnumFile.TurnStatus.SKILL) {
                selectedSkill = skill;
                showTargets();
            } else {
                Log.d(TAG, "Can not change turnstatus = skill");
                highlightSkill(-1);
            }
        } else {
            Log.d(TAG, "Choose a character first to select a skill");
            highlightSkill(-1);
        }
    }

    /* Return true if a piece is valid character */
    private boolean validCharacter() {
        return 0 <= selectedChar && selectedChar <= 17;
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
        resetSkills();
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
        } else if (result == 2) {
            Intent results = new Intent(this, Result.class);
            results.putExtra("win", false);
            startActivity(results);
        } else if (result == -1) {
            Log.d(TAG, "SOMETHING WENT VERY VERY WRONG AHHHHHHHH");
        }
        // else continue playing
    }

    /* Changes the available skill icon based on available characters */
    private void displaySkills(int row, int col) {
        ArrayList<EnumFile.SkillsEnum> skillList = boardLogic.getAdventurerSkills(row, col);
        int skillListSize = skillList.size();
        Log.d(TAG, "Skill list size: " + skillList.size());
        if (skillListSize >= 1) {
            firstSkillButton.setImageResource(selectSkill(skillList.get(0)));
            firstSkill = skillList.get(0);
        }
        if (skillListSize >= 2) {
            secondSkillButton.setImageResource(selectSkill(skillList.get(1)));
            secondSkill = skillList.get(1);
        }
        if (skillListSize >= 3) {
            thirdSkillButton.setImageResource(selectSkill(skillList.get(2)));
            thirdSkill = skillList.get(2);
        }
        if (skillListSize >=4) {
            fourthSkillButton.setImageResource(selectSkill(skillList.get(3)));
            fourthSkill = skillList.get(3);
        }
    }

    private int selectSkill(EnumFile.SkillsEnum skill) {
        int image = -1;
        switch(skill) {
            case FIREBALL:
                Log.d(TAG, "Changing skill image to fireball");
                return R.drawable.fireball;
            case HEAL:
                Log.d(TAG, "Changing skill image to heal");
                return R.drawable.first_aid;
            case LIGHTNING:
                Log.d(TAG, "Changing skill image to lightning");
                return R.drawable.lightning;
            case MOVE:
                Log.d(TAG, "Changing skill image to move");
                return R.drawable.move;
            case PUNCH:
                Log.d(TAG, "Changing skill image to punch");
                return R.drawable.punch;
            case STRIKE:
                Log.d(TAG, "Changing skill image to melee");
                return R.drawable.melee;
            case BLOCK:
                Log.d(TAG, "Changing skill image to shield");
                return R.drawable.shield;
            default:
                Log.d(TAG, "Something went wrong when selecting a skill!");
        }
        return image;
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
                touchedInactiveCharacter(row, col);
            } else {
                Log.d(TAG, log + "What");
            }
            return false;
        }
    };

    /* Touched an inactive character */
    private void touchedInactiveCharacter(int row, int col) {
        if (selectedSkill == EnumFile.SkillsEnum.HEAL) {
            boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row * 3 + col), selectedSkill);
        } else {
            Toast.makeText(getApplicationContext(),
                    "You already used this character", Toast.LENGTH_SHORT).show();
        }
    }

    /* Clicked on empty spot on grid */
    private void touchedEmpty(int row, int col, String log) {
        /* If moving */
        if (turnStatus == EnumFile.TurnStatus.SKILL && selectedSkill == EnumFile.SkillsEnum.MOVE) {
            if (possibleTargets.contains(row*3+col)) {
                Log.d(TAG, log + ", target is valid spot to move to");
                boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row * 3 + col), selectedSkill);
                delayCleanUp();
            } else {
                Log.d(TAG, log + ", cannot move here");
            }
        }
    }

    /* Selected enemy's character */
    private void touchedEnemy(int row, int col, String log) {
        if (turnStatus == EnumFile.TurnStatus.SKILL && selectedSkill != EnumFile.SkillsEnum.INVALID
                && possibleTargets.contains(row*3+col)) {
            Log.d(TAG, log + ", selected computer's character, using skill " + selectedSkill);
            boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row * 3 + col), selectedSkill);
            delayCleanUp();
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
            displaySkills(row, col);
            boardView.invalidate();
        /* If it is time to select a target */
        } else if (turnStatus == EnumFile.TurnStatus.SKILL && selectedSkill != EnumFile.SkillsEnum.INVALID) {
            /* Check if selected adventurer is in the target list */
            if (possibleTargets.contains(row*3+col)) {
                Log.d(TAG, log + ", target is valid to use skill on");
                boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row * 3 + col), selectedSkill);
                delayCleanUp();
            } else {
                Log.d(TAG, log + ", target is not valid to use skill on");
            }
        }
    }

    private Runnable createRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                changeTurn();
                boardView.invalidate();
            }
        };
    }

    /* Computer clean up method */
    private void delayCleanUp() {
//        turnStatus = EnumFile.TurnStatus.ENEMY;
        boardView.setTargets(null);
        boardView.setCharacter(-1);
        boardLogic.checkGameOver();
        boardView.invalidate();
        myRunnable = createRunnable();
        mPauseHandler.postDelayed(myRunnable, 1000);
//                changeTurn();
    }

    @Override
    protected void onResume(){
        super.onResume();
//        mBackgroundSound = new BackgroundSound();
//        mBackgroundSound.execute();
        Log.d(TAG, "RESUME");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "STOP");
        mBackgroundSound.end();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        mBackgroundSound = new BackgroundSound();
        mBackgroundSound.execute();
        Log.d(TAG, "RESTART");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "STARTING");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBackgroundSound.cancel(true);
        Log.d(TAG, "DESTROY");
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "Back pressed");
        return;
    }

    /* Code from this stack overflow thread here http://stackoverflow.com/questions/12241474/asynctask-music-not-stopping-when-power-button-pressed */
    public class BackgroundSound extends AsyncTask<Void, Void, Void> {

        private MediaPlayer player;

        protected void onPreExecute() {
//            if (playMusic)
            player = MediaPlayer.create(TacticsGame.this, R.raw.bgm);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }

            });
        }

        @Override
        protected Void doInBackground(Void... params) {
            player.setLooping(true); // Set looping
            player.setVolume(1.0f, 1.0f);
            player.start();
            return null;
        }

        public void end(){
            player.stop();
            player.release();
        }
    }
}

