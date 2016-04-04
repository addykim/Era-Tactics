package askim.eratactics.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowAnimationFrameStats;
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

    private static final String TAG = "TacticsGame";

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




    private ImageView moveButton;

    private ImageView punchButton;

    private int normalSkill;
    private ImageView normalButton;

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
        boardLogic.setBoardView(boardView);

        // Listen for touches on the board
        boardView.setOnTouchListener(mTouchListener);

        // Setup click listener for each skill buttons
        moveButton = (ImageView) findViewById(R.id.move);
        moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSkill = EnumFile.SkillsEnum.MOVE;
                showTargets(checkSkillTime());
                Log.d(TAG, "Move clicked");
            }
        });
        punchButton = (ImageView) findViewById(R.id.punch);
        punchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedSkill = EnumFile.SkillsEnum.PUNCH;
                showTargets(checkSkillTime());
                Log.d(TAG, "Punch clicked");
            }
        });
        normalButton = (ImageView) findViewById(R.id.thirdSkill);
        normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO get skill
//                selectedSkill =
                checkSkillTime();
                Log.d(TAG, "Third skill clicked");
            }
        });
    }

    /* Creates a new game */
    private void newGame() {
        Log.d(TAG, "Creating new game");
//        TODO replace newBoard(new Team) with clearboard command
        // Redraw//       boardView.invalidate();
        playersTurn = true;
        turnStatus = EnumFile.TurnStatus.CHARACTER;
        selectedChar = -1;
        selectedCharRow = -1;
        selectedCharCol = -1;
        possibleTargets = new ArrayList<Integer>();
    }

    /* Switch player turn to computer turn or vice versa */
    private void changeTurn() {
        Log.d(TAG, "It is now the computer turn");
        // If out of active pieces, reset all the pieces and try make move again
        if (!boardLogic.makeComputerMove()) {
            boardLogic.resetTurn();
            boardLogic.makeComputerMove();
        }
        Log.d(TAG, "It is now the player's turn");
        turnStatus = EnumFile.TurnStatus.CHARACTER;
        selectedChar = -1;
        selectedCharCol = -1;
        selectedCharRow = -1;
        boardView.setCharacter(selectedChar);
        boardView.setTargets(null);
        boardView.invalidate();
        /* Dead pieces are also removed in checkGameOver */
        Log.d(TAG, "Checking for winner");
        boardLogic.checkGameOver();
    }

    /* Changes the available skill icon based on available characters */
    private void displaySkills(int row, int col) {
        boardLogic.getAdventurerSkills(row, col);

//      TODO based on the ArrayList<EnumFile.SkillsEnum> returned, iterate through the ImageViews to change the source
//        skillButton.setImageResource(R.drawable.new_image);
//        skillButton =
    }

    /* Check player's turn and if it is time to use a skill
     * Increment turnStatus from player to skill so prevent going back
     * Returns true if switching from character move to skill */
    private boolean checkSkillTime() {
        if (playersTurn && turnStatus == EnumFile.TurnStatus.CHARACTER) {
            turnStatus = EnumFile.TurnStatus.SKILL;
            return true;
        }
        Log.d(TAG, "Can not change to skill turn status");
        return false;
    }

    /* Circles available targets to use skill on */
    public void showTargets(boolean skillTime) {
        possibleTargets =
                boardLogic.availableTargets(selectedCharRow, selectedCharCol, selectedSkill);
        for (int index=0; index<possibleTargets.size(); index++) {
            /* Disable moving to or healing enemy side */
            if ((selectedSkill == EnumFile.SkillsEnum.MOVE || selectedSkill == EnumFile.SkillsEnum.HEAL)
                    && (possibleTargets.get(index)/3) <= 2) {
                // TODO this is still bugged and does not get rid of all the possible move spots
                possibleTargets.remove(index);
            }
        }
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
                /* If moving */
                if (turnStatus == EnumFile.TurnStatus.SKILL && selectedSkill == EnumFile.SkillsEnum.MOVE) {
                    if (possibleTargets.contains(row*3+col) && row>=3) {
                        Log.d(TAG, log + ", target is valid spot to move to");
                        boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row*3+col), selectedSkill);
                        boardView.moveBitmapImage(selectedCharRow, selectedCharCol, row, col);
                        changeTurn();
                    } else {
                        Log.d(TAG, log + ", cannot move here");
                    }
                }
            /* Selecting an enemy's character */
            } else if (boardLogic.resolveGrid(row,col) == 1) {
                Log.d(TAG, log + ", selected computer's character, using skill " + selectedSkill);
                boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row*3+col), selectedSkill);
                changeTurn();
            } else if (boardLogic.resolveGrid(row, col) == 2) {
                /* Selecting a character */
                if (turnStatus == EnumFile.TurnStatus.CHARACTER) {
                    log += ", selected player's character";
                    selectedCharRow = row;
                    selectedCharCol = col;
                    selectedChar = row*3+col;
                    boardView.setCharacter(selectedChar);
                    boardView.invalidate();
                /* If it is time to select a target */
                } else if (turnStatus == EnumFile.TurnStatus.SKILL) {
                    /* Check if selected adventurer is in the target list */
                    if (possibleTargets.contains(row*3+col)) {
                        Log.d(TAG, log + ", target is valid to use skill on");
                        boardLogic.resolveSkill(selectedCharRow, selectedCharCol, (row * 3 + col), selectedSkill);
                        changeTurn();
                    } else {
                        Log.d(TAG, log + ", target is not valid to use skill on");
                    }
                }
            } else if (boardLogic.resolveGrid(row, col) == 3) {
                Toast.makeText(getApplicationContext(),
                        "You already used this character", Toast.LENGTH_SHORT).show();
            } else {
//            TODO what if -1 is returned from resolve grid
                Log.d(TAG, log + "What");
            }
            return false;
        }
    };
}
