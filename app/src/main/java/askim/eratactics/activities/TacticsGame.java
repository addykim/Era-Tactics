package askim.eratactics.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import askim.eratactics.R;
import askim.eratactics.gamelogic.Adventurer;
import askim.eratactics.gamelogic.Board;
import askim.eratactics.gamelogic.EnumFile;
import askim.eratactics.gamelogic.Equipment;
import askim.eratactics.gamelogic.Team;
import askim.eratactics.views.BoardView;
import askim.eratactics.views.Resources;
import askim.eratactics.views.SkillView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TacticsGame extends AppCompatActivity {

    private static final String TAG = "TacticsGame";

    /* Draws the board based on boardLogic */
    private BoardView boardView;

    /* Draws the skills based on character selected */
    private SkillView skillView;

    /* Actual game logic related to the board */
    private Board boardLogic;

    private EnumFile.TurnStatus turnStatus;

    private int selectedChar;
    private int selectedCharRow;
    private int selectedCharCol;
    private EnumFile.SkillsEnum selectedSkill;
    private ArrayList<Integer> possibleTargets;

    /* Creates delay for during computer's turn */
    private Handler mPauseHandler;
    private Runnable myRunnable;

    private TextView prompt;

    /* Elements necessary to play music */
    private BackgroundSound mBackgroundSound;
    private MediaPlayer sfxPlayer;
    private boolean playMusic;
    private boolean playSfx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tactics_game);

        /* Hide action bar */
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        Intent intent = getIntent(); // gets the previously created intent

        SharedPreferences mPrefs = getSharedPreferences(SettingsActivity.PREFS_NAME, MODE_PRIVATE);

        /* Initialize music */
        playMusic = mPrefs.getBoolean("music", false);
//        if (playMusic) {
//            mBackgroundSound = new BackgroundSound();
//            mBackgroundSound.execute();
//        }
        playSfx = mPrefs.getBoolean("sfx", false);
        if (playSfx)
            sfxPlayer = new MediaPlayer();

        mPauseHandler = new Handler();

        prompt = (TextView) findViewById(R.id.textPrompt);
        newGame();

        /* Hard coded team composition */
        Team alphaTeam = new Team();
        Adventurer villager1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.VILLAGER)}, "Bob");
        Adventurer apprentice1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.APPRENTICE),
                                                                new Equipment(EnumFile.Equipments.BASIC_POTION)}, "BOB!");
        Adventurer magician1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.MAGICIAN),
                                                              new Equipment(EnumFile.Equipments.BASIC_WAND),
                                                              new Equipment(EnumFile.Equipments.BASIC_POTION)}, "Still Bob");
        Adventurer archer1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.ARCHER),
                                                            new Equipment(EnumFile.Equipments.BASIC_ARROW)}, "Uh.. Bob.");
        alphaTeam.addTeamMember(villager1);
        alphaTeam.putAdventurer(villager1, 2, false);
        alphaTeam.addTeamMember(apprentice1);
        alphaTeam.putAdventurer(apprentice1, 6, false);
        alphaTeam.addTeamMember(magician1);
        alphaTeam.putAdventurer(magician1, 3, false);
        alphaTeam.addTeamMember(archer1);
        alphaTeam.putAdventurer(archer1, 5, false);


//        boardLogic = new Board(new Team());
        // Gets level selected
//        int level = 2;
        // TODO enable once multiple levels are available
        int level = intent.getIntExtra("level", -1);
        boardLogic = new Board(alphaTeam, level);
        boardView = (BoardView) findViewById(R.id.board);
        boardView.setGame(boardLogic);

        // Listen for touches on the board
        boardView.setOnTouchListener(boardTouchListener);

        skillView = (SkillView) findViewById(R.id.skillList);
        skillView.setOnTouchListener(skillTouchListener);

        // TODO remove for beta
        Button instaWin = (Button) findViewById(R.id.instaWin);
        instaWin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent results = new Intent(getApplicationContext(), ResultActivity.class);
                results.putExtra("win", true);
                startActivity(results);
                finish();
            }
        });

        ImageView tutorial = (ImageView) findViewById(R.id.gameTutorial);
        tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO pass point of the tutorail into
                Intent tutorialIntent = new Intent(getApplicationContext(), TutorialActivity.class);
                startActivity(tutorialIntent);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "onSaveInstanceState");
        // TODO save instance state
    }

    /* Return true if a piece is valid character */
    private boolean validCharacter() {
        return 0 <= selectedChar && selectedChar <= 17;
    }

    /* Creates a new game */
    private void newGame() {
        Log.d(TAG, "Creating new game");
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
        checkForWinner();
        // TODO future if the player or computer is out of moves but the other is not, then skip the person who is out of turn until the other is out also
        // If out of active pieces, reset all the pieces and try make move again
        do {
            boardLogic.makeComputerMove();
        } while (boardLogic.getActivePlayers() == 0 && boardLogic.getActiveEnemies() != 0);

        if (boardLogic.getActiveEnemies() == 0 && boardLogic.getActivePlayers() == 0) {
            Log.d(TAG, "Resetting all the piece's turn");
            boardLogic.resetTurn();
            Toast.makeText(getApplicationContext(), "New Round", Toast.LENGTH_SHORT).show();
        }

        /* Reset everything for the player's turn */
        resetValues();
        boardView.setTargets(null);
        boardView.setCharacter(selectedChar);
        boardView.invalidate();

        checkForWinner();
    }

    /* Dead pieces are also removed in checkGameOver */
    public void checkForWinner() {
//        Log.d(TAG, "Checking for winner");
        int result = boardLogic.checkGameOver();
        if (result == 1) {
            //TODO replace with actual intent to win or lose screen
            Intent results = new Intent(this, ResultActivity.class);
            results.putExtra("win", true);
            startActivity(results);
            finish();
        } else if (result == 2) {
            Intent results = new Intent(this, ResultActivity.class);
            results.putExtra("win", false);
            startActivity(results);
            finish();
        } else if (result == -1) {
            Log.d(TAG, "SOMETHING WENT VERY VERY WRONG AHHHHHHHH");
        }
        // else continue playing
    }

    /* Circles available targets to use skill on */
    public void showTargets() {
        possibleTargets =
                boardLogic.availableTargets(selectedCharRow, selectedCharCol, selectedSkill);
        boardView.setTargets(possibleTargets);
        boardView.invalidate();
    }

    // Listen for touches on the skill list
    // Adapted from in class code
    private View.OnTouchListener skillTouchListener = new View.OnTouchListener() {
        /* Rows 1-3 are for the enemies, 4-6 are for the player */
        public boolean onTouch(View v, MotionEvent event) {
            int skillNum = (int) event.getY() / skillView.getSkillCellHeight();
            selectedSkill = skillView.setSkill(skillNum);
            executeSkill(selectedSkill);
            return false;
        }
    };

    /* Execute a skill if a valid character was selected
     * Changes turn status to skill so another character can not be selected */
    public void executeSkill(EnumFile.SkillsEnum skill) {
        if (validCharacter()) {
            if (skill != null) {
                if (turnStatus == EnumFile.TurnStatus.CHARACTER) {
                    turnStatus = EnumFile.TurnStatus.SKILL;
                    selectedSkill = skill;
                    showTargets();
                } else if (turnStatus == EnumFile.TurnStatus.SKILL) {
                    selectedSkill = skill;
                    showTargets();
                } else if (turnStatus == EnumFile.TurnStatus.ENEMY) {
                    Log.d(TAG, "Enemy's turn cannot execute skill");
                } else {
                    Log.d(TAG, "Can not change turnstatus = skill");
                }
            } else {
                Toast.makeText(this, "This is not a valid skill!", Toast.LENGTH_SHORT).show();
                //TODO select a valid skill, then select invalid skill causes crash
            }
        } else {
            Log.d(TAG, "Choose a character first to select a skill");
            Toast.makeText(this, "Select a character before selecting a skill", Toast.LENGTH_LONG).show();
        }
    }


    // Listen for touches on the board
    // Adapted from in class code
    private View.OnTouchListener boardTouchListener = new View.OnTouchListener() {
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
                touchedInactiveCharacter(row, col);
            } else {
                Log.d(TAG, log + "What");
            }
            return false;
        }
    };

    /* Touched an inactive character */
    private void touchedInactiveCharacter(int row, int col) {
        if (selectedSkill == EnumFile.SkillsEnum.HEAL && turnStatus == EnumFile.TurnStatus.SKILL) {
            boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row*3+col), selectedSkill);
            delayCleanUp();
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
            playSFX();
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
            skillView.setSkillList(boardLogic.getAdventurerSkills(row, col));
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
                changePrompt(false);
//                boardView.invalidate();
            }
        };
    }

    /* Computer clean up method */
    private void delayCleanUp() {
        turnStatus = EnumFile.TurnStatus.ENEMY;

        // Nullify everything
        boardView.setTargets(null);
        boardView.setCharacter(-1);
        boardLogic.checkGameOver();
        boardView.invalidate();
        skillView.nullifySkills();

        // Do delay for computer
        changePrompt(true);
        myRunnable = createRunnable();
        mPauseHandler.postDelayed(myRunnable, 1000);
    }

    /* If the setting to play sfx is on, then the game will play sound effects */
    public void playSFX() {
        if (playSfx) {
            int soundToPlay = Resources.getSkillSoundId(selectedSkill);
            if (sfxPlayer == null || !sfxPlayer.isPlaying()) {
                sfxPlayer.create(this, soundToPlay);
                sfxPlayer.start();
            }
        }
    }


    /* Manages the changePrompt for the game. */
    public void changePrompt(boolean dull) {
        String text = getResources().getString(R.string.playersTurn);
        if (turnStatus == EnumFile.TurnStatus.ENEMY) {
            text = getResources().getString(R.string.computersTurn);
            Log.d(TAG, "Computer's turn");
        }
        // Set things back to normal to indicate it is now the character's turn
        boardView.dullBoard(dull);
        skillView.dullSkills(dull);
        prompt.setText(text);
        prompt.invalidate();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "ON PAUSE");
        if (playMusic)
            mBackgroundSound.end();
        if (playSfx) {}
        // TODO release sfx
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "RESUME");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "STOP");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "RESTART");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "STARTING");
        if (playMusic) {
            mBackgroundSound = new BackgroundSound();
            mBackgroundSound.execute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (playMusic)
            mBackgroundSound.cancel(true);
//        if (playSfx)
//            sfxPlayer.release();
        Log.d(TAG, "DESTROY");
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.d(TAG, "BACK PRESSED");
    }

    /* Code from this stack overflow thread here http://stackoverflow.com/questions/12241474/asynctask-music-not-stopping-when-power-button-pressed */
    public class BackgroundSound extends AsyncTask<Void, Void, Void> {

        private MediaPlayer player;

        protected void onPreExecute() {
            player = MediaPlayer.create(TacticsGame.this, R.raw.bgm);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {}});
            player.setLooping(true); // Set looping
        }

        @Override
        protected Void doInBackground(Void... params) {
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

