package askim.eratactics.gamelogic;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import askim.eratactics.views.BoardView;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Board {

    private final String TAG = "BoardLogic";

    int level;
    private Piece[][] pieces;
    int activeEnemies = 0;
    int activePlayers = 0;

    public Board(Team team, int lvl, Context mContext) {
        pieces = new Piece[6][3];
        level = lvl;
        // Iterate through the team members and place them on board
        for (Adventurer member: team.getTeamMembers()) {
            Piece p;
            if (team.getLeader() == member) {
                p = new Piece(member, true);
            } else {
                p = new Piece(member, false);
            }
            Log.d(TAG, "Member.position: " + member.getPosition());
            Log.d(TAG, "Placing " + member.getAdventurerClassAsString() + " into Pieces at " +
                    (member.getPosition()/3+3) + ", " + (member.getPosition()%3));
            // TODO do I need to call skills here for adventurers?
            pieces[member.getPosition()/3+3][member.getPosition()%3] = p;
            activePlayers++;
        }
        Log.d(TAG, "Active players: " + activePlayers);
        generateEnemies();
    }

    private void generateEnemies() {
        Log.d(TAG, "Generating enemies");
        ArrayList<Piece> tempPieces = (ArrayList) Piece.find(Piece.class, "level = ?", Integer.toString(level));
        Log.d(TAG, "Size of tempPieces: " + tempPieces.size());
        activeEnemies = 0;
        for (Piece piece: tempPieces) {
            pieces[piece.getPosition()/3][piece.getPosition()%3] = piece;
            piece.setSkillEnums();
            activeEnemies++;
        }
    }

    // Returns who is in cell of grid
    // returns 0 if nothing in grid, 1 if enemy is in grid, 2 if adventurer, 3 if inactive adventurer
    public int resolveGrid(int row, int col) {
        if (pieces[row][col] == null) {
            return 0;
        } else if (!pieces[row][col].getIsPlayer()) {
            return 1;
        } else if (pieces[row][col].getIsPlayer() && !pieces[row][col].isHasMoved()) {
            return 2;
        } else if (pieces[row][col].getIsPlayer() && pieces[row][col].isHasMoved()) {
            return 3;
        } else
            return -1;
    }

    /**
     * Get the skills of a given adventurer
     * @param row - the current row of requested piece
     * @param col - the current col of requested piece
     * @return an arraylist of skills that the adventurer has
     */
    public ArrayList<EnumFile.SkillsEnum> getAdventurerSkills(int row, int col) {
        return pieces[row][col].getSkills();
    }

    /**
     * Get the available targets of a given adventurer and skill
     * @param row
     * @param col
     * @param skill
     * @return
     */
    public ArrayList<Integer> availableTargets(int row, int col, EnumFile.SkillsEnum skill) {
        int range;
        EnumFile.TargetType targetType;

        switch (skill) {
            case MOVE:
                range = pieces[row][col].mrg;
                targetType = EnumFile.TargetType.EMPTY;
                Log.d(TAG, "move range is " + range);
                break;
            case PUNCH:
                range = pieces[row][col].atr;
                targetType = EnumFile.TargetType.ENEMY;
                break;
            case FIREBALL:
                range = 2;
                targetType = EnumFile.TargetType.ENEMY;
                break;
            case STRIKE:
                range = pieces[row][col].atr + 1;
                targetType = EnumFile.TargetType.ENEMY;
                break;
            case LIGHTNING:
                range = pieces[row][col].atr + 4;
                targetType = EnumFile.TargetType.ENEMY;
                break;
            case HEAL:
                range = 3;
                targetType = EnumFile.TargetType.SELF_ALLY;
                break;
            default:
                range = -1;
                targetType = null;
                break;
        }

        ArrayList<Integer> targets = new ArrayList<Integer>();
        for (int i = 0; i < 18; i++) {
            int r = i / 3;
            int c = i % 3;

            if (Math.abs(r - row) + Math.abs(c - col) <= range) {
                switch (targetType) {
                    case SELF:
                        targets.add(row * 3 + col);
                        return targets;
                    case ENEMY:
                        if (pieces[r][c] != null && pieces[r][c].getIsPlayer() != pieces[row][col].getIsPlayer()) {
                            targets.add(i);
                        }
                        break;
                    case ALLY:
                        if (pieces[r][c] != null && (pieces[r][c].getIsPlayer() == pieces[row][col].getIsPlayer()) && (row * 3 + col != i)) {
                            targets.add(i);
                        }
                        break;
                    case SELF_ALLY:
                        if (pieces[r][c] != null && (pieces[r][c].getIsPlayer() == pieces[row][col].getIsPlayer())) {
                            targets.add(i);
                        }
                        break;
                    case ENEMY_EMPTY:
                        if (pieces[r][c] == null || pieces[r][c].getIsPlayer() != pieces[row][col].getIsPlayer()) {
                            targets.add(i);
                        }
                        break;
                    case EMPTY:
                        if (pieces[r][c] == null) {
                            targets.add(i);
                        }
                        break;
                    default:
                        Log.d("Board Class", "Oh no! TargetType is bad!");
                        break;
                }
            }

        }
        return targets;
    }


    /* Stick value from available target into dest */
    public void resolveSkill(int row, int col, int dest, EnumFile.SkillsEnum skill) {

        int destRow = dest / 3;
        int destCol = dest % 3;
        double damage;

        switch (skill) {
            case MOVE:
                pieces[destRow][destCol] = pieces[row][col];
                pieces[row][col] = null;
                row = destRow;
                col = destCol;
                break;

            case PUNCH:
                damage = (pieces[row][col].atk - pieces[destRow][destCol].def);
                if (damage > 0)
                    pieces[destRow][destCol].hp -= damage;
                break;

            case LIGHTNING:
                damage = (pieces[row][col].atk - pieces[destRow][destCol].def) * 1.5;
                if (damage > 0)
                    pieces[destRow][destCol].hp -= damage;
                break;

            case FIREBALL:
                if (pieces[destRow][destCol] == null)
                    damage = pieces[row][col].atk * 1.3;
                else {
                    damage = (pieces[row][col].atk - pieces[destRow][destCol].def) * 1.3;
                    if (damage > 0)
                        pieces[destRow][destCol].hp -= damage;
                }
                // area damage
                if (damage > 0)
                    damage = damage * 0.3;
                else
                    damage = 0;
                if (destRow > 0 && resolveGrid(row, col) != resolveGrid(destRow - 1, destCol)) {
                    if (pieces[destRow - 1][destCol] != null)
                        pieces[destRow - 1][destCol].hp -= damage;
                }
                if (destCol > 0 && resolveGrid(row, col) != resolveGrid(destRow, destCol - 1)) {
                    if (pieces[destRow][destCol - 1] != null)
                        pieces[destRow][destCol - 1].hp -= damage;
                }
                if (destRow < 5 && resolveGrid(row, col) != resolveGrid(destRow + 1, destCol)) {
                    if (pieces[destRow + 1][destCol] != null)
                        pieces[destRow + 1][destCol].hp -= damage;
                }
                if (destCol < 2 && resolveGrid(row, col) != resolveGrid(destRow, destCol + 1)) {
                    if (pieces[destRow][destCol + 1] != null)
                        pieces[destRow][destCol + 1].hp -= damage;
                }
                break;

            case HEAL:
                pieces[destRow][destCol].hp += pieces[destRow][destCol].mag + pieces[row][col].mag;
                if (pieces[destRow][destCol].hp > pieces[destRow][destCol].maxHp) {
                    pieces[destRow][destCol].hp = pieces[destRow][destCol].maxHp;
                }
                break;
        }

        if (pieces[row][col].getIsPlayer())
            activePlayers--;
        pieces[row][col].moved();
    }

    /**
     * Checks the HP of each piece and remove them if they are dead.
     * Return if the game is over after cleaning up the board.
     * @return 0 if continue
     *         1 if player won
     *         2 if computer won
     */
    public int checkGameOver() {
        int playerCount = 0;
        int enemyCount = 0;
        activeEnemies = 0;
        activePlayers = 0;

        // remove dead pieces
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 3; c++) {
                if (pieces[r][c] != null) {
                    if (pieces[r][c].hp <= 0) {
                        pieces[r][c] = null;
                    }
                    else {
                        if (pieces[r][c].getIsPlayer()) {
                            playerCount++;
                            if (!pieces[r][c].isHasMoved()) {
                                activePlayers++;
                            }
                        }
                        else {
                            enemyCount++;
                            if (!pieces[r][c].isHasMoved()) {
                                activeEnemies++;
                            }
                        }
                    }
                }
            }
        }
        if (enemyCount == 0)
            return 1;
        else if (playerCount == 0)
            return 2;
        return 0;
    }

    /**
     *
     * @return false if no available pieces
     *         true if made a move
     */
    public boolean makeComputerMove() {
        if (activeEnemies <= 0) {
            Log.d(TAG, "OH NO Computer cannot move because the number of active enemies is <= 0!");
            return false;
        }
        int enemyNumber = ((int)(Math.random()*activeEnemies)) +1;
        Log.d(TAG,"Randomly selected the enemy # " + enemyNumber);
        int enemiesEncountered = 0;
        int movingR = -1;
        int movingC = -1;

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 3; c++) {
                Piece p = pieces[r][c];
                if (p != null && !p.getIsPlayer() && !p.isHasMoved()) {
                    enemiesEncountered++;
                    if (enemiesEncountered == enemyNumber) {
                        movingR = r;
                        movingC = c;
                        Log.d(TAG,"Going to move enemy on " + movingR + ", " + movingC);
                    }
                }
            }
        }

        Piece enemyPiece = pieces[movingR][movingC];
        ArrayList<EnumFile.SkillsEnum> enemySkills = getAdventurerSkills(movingR, movingC);

        // TODO this crashes probably because it's not saving on creation/not being grabbed
        for (EnumFile.SkillsEnum s : enemySkills) {
            if (enemyPiece != null) {
                // if the moving enemy has HEAL and there is an enemy low on health (<20%), heal!
                if (s == EnumFile.SkillsEnum.HEAL) {
                    ArrayList<Integer> targets = availableTargets(movingR, movingC, s);
                    for (int t : targets) {
                        int r = t / 3;
                        int c = t % 3;

                        if (pieces[r][c].hp < pieces[r][c].maxHp * 0.2) {
                            resolveSkill(movingR, movingC, t, s);
                            pieces[movingR][movingC].moved();
                            activeEnemies--;
                            Log.d(TAG,"Healed enemy on " + r + ", " + c
                                    + ". Number of active enemies is now " + activeEnemies);

                            return true;
                        }
                    }
                }
                // if there're players in range, attack the weakest one!
                if (s != EnumFile.SkillsEnum.MOVE) {
                    ArrayList<Integer> targets = availableTargets(movingR, movingC, s);
                    int target = -1;
                    for (int t : targets) {
                        int tr = t / 3;
                        int tc = t % 3;
                        if (resolveGrid(tr, tc) == 2 || resolveGrid(tr, tc) == 3) {
                            if (target == -1) {
                                target = t;
                            } else if (pieces[tr][tc].hp < pieces[target / 3][target % 3].hp) {
                                target = t;
                            }
                        }
                    }
                    if (target != -1) {
                        resolveSkill(movingR, movingC, target, s);
                        pieces[movingR][movingC].moved();
                        activeEnemies--;
                        Log.d(TAG,"Attacked player on " + (target / 3) + ", " + (target % 3)
                                + ". Number of active enemies is now " + activeEnemies);
                        return true;
                    }
                }
            }
        }
        // if didn't get to heal or attack, then move! randomly!
        ArrayList<Integer> targets = availableTargets(movingR, movingC, EnumFile.SkillsEnum.MOVE);
        if (targets.size() == 0) {
            // This sad enemy piece can't do anything, skip its turn!
            Log.d(TAG,"Did not find any available moves for enemy " + movingR + ", " + movingC
                    + ". Turn skipped. Number of active enemies is now " + activeEnemies);
            pieces[movingR][movingC].moved();
            activeEnemies--;
            return true;
        }
        int numTargets = targets.size();
        int moveTo = (int)(Math.random()*numTargets);
        resolveSkill(movingR, movingC, targets.get(moveTo), EnumFile.SkillsEnum.MOVE);
        // pieces[moveTo / 3][moveTo % 3].moved();
        // If computer is not moving correctly visually I may have set the destination wrong
//        boardView.moveBitmapImage(movingR, movingC, moveTo/3, moveTo%3);
        activeEnemies--;
        Log.d(TAG,"Moved to grid number " + targets.get(moveTo)
                + ". Number of active enemies is now " + activeEnemies);
        return true;
    }

    /**
     * After every piece has taken a move, a new turn starts
     * moved pieces can take moves again.
     */
    public void resetTurn() {
        activePlayers = 0;
        activeEnemies = 0;
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 3; c++) {
                if (pieces[r][c] != null) {
                    pieces[r][c].resetPiece();
                    if (pieces[r][c].getIsPlayer()) {
                        activePlayers++;
                    }
                    else
                        activeEnemies++;
                }
            }
        }

    }

    public Piece getBoardOccupant(int row, int col) {
//        Log.d(TAG, "Board occupant " + pieces[row][col]);
        return pieces[row][col];
    }

    private String printBoard() {
        String s = "";
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 3; c++) {

            }
        }
        return null;
    }

    /* This will be used in order to call boardview changes whenever enemy moves */
//    public void setBoardView(BoardView view) { boardView = view; }

    public int getActiveEnemies() {
        return activeEnemies;
    }

    public int getActivePlayers() {
        return activePlayers;
    }

    /**
     * Get the current hp of a given adventurer or enemy
     * @param row - current row position of the requested piece
     * @param col - current col position of the requested piece
     * @return hp - if given position has a valid piece
     *         -1 - if the given position is empty
     */
    public int getHp(int row, int col) {
        if (pieces[row][col] != null){
            return pieces[row][col].hp;
        }
        else
            return -1;
    }

    /**
     * Get the current max hp of a given adventurer or enemy
     * @param row - current row position of the requested piece
     * @param col - current col position of the requested piece
     * @return hp - if given position has a valid piece
     *         -1 - if the given position is empty
     */
    public int getMaxHp(int row, int col) {
        if (pieces[row][col] != null){
            return pieces[row][col].maxHp;
        }
        else
            return -1;
    }
}
