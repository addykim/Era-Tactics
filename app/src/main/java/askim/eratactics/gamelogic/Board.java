package askim.eratactics.gamelogic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Board {

    private Piece[][] pieces;
    int activeEnemies = 0;

    public Board(Team team) {
        pieces = new Piece[6][3];
        for (int i = 0; i < 9; i++) {
            Adventurer temp = team.getAdventurer(i);
            if (temp != null) {
                Piece p;
                if (team.getLeader() == temp) {
                    p = new Piece(temp, true);
                } else {
                    p = new Piece(temp, false);
                }
                pieces[i / 3 + 3][i % 3] = p;
            }
        }
        generateEnemies();
    }

    private void generateEnemies() {
        // 4 enemies
        pieces[0][0] = new Piece();
        pieces[0][2] = new Piece();
        pieces[1][1] = new Piece();
        pieces[2][2] = new Piece();
    }

    // Returns who is in cell of grid
    // returns 0 if nothing in grid, 1 if enemy is in grid, 2 if adventurer, 3 if inactive adventurer
    public int resolveGrid(int row, int col) {
        if (pieces[row][col] == null) {
            return 0;
        }
        else if (!pieces[row][col].getIsPlayer()) {
            return 1;
        }
        else if (pieces[row][col].getIsPlayer() && !pieces[row][col].isHasMoved()) {
            return 2;
        }
        else if (pieces[row][col].getIsPlayer() && pieces[row][col].isHasMoved()) {
            return 3;
        }
        else
            return -1;
    }


    public ArrayList<EnumFile.SkillsEnum> getAdventurerSkills(int row, int col) {
        return pieces[row][col].getSkills();
    }

    // return a list of integers between 0-17 corresponding to the 18 grids on the board.
    public ArrayList<Integer> availableTargets(int row, int col, EnumFile.SkillsEnum skill) {
        int range;
        EnumFile.TargetType targetType;

        switch (skill) {
            case MOVE:
                range = pieces[row][col].mrg;
                targetType = EnumFile.TargetType.EMPTY;
                break;
            case FIREBALL:
                range = 2;
                targetType = EnumFile.TargetType.ENEMY_EMPTY;
                break;
            case LIGHTNING:
                range = 4;
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
                        if (pieces[r][c] != null && !pieces[r][c].getIsPlayer()) {
                            targets.add(i);
                        }
                        break;
                    case ALLY:
                        if (pieces[r][c] != null && pieces[r][c].getIsPlayer() && (row * 3 + col != i)) {
                            targets.add(i);
                        }
                        break;
                    case SELF_ALLY:
                        if (pieces[r][c] != null && pieces[r][c].getIsPlayer()) {
                            targets.add(i);
                        }
                        break;
                    case ENEMY_EMPTY:
                        if (pieces[r][c] == null || !pieces[r][c].getIsPlayer()) {
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

    public void resolveSkill(int row, int col, int dest, EnumFile.SkillsEnum skill) {

        int destRow = dest / 3;
        int destCol = dest % 3;
        double damage;


        switch (skill) {
            case MOVE:
                pieces[destRow][destCol] = pieces[row][col];
                pieces[row][col] = null;
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
                    pieces[destRow - 1][destCol].hp -= damage;
                }
                if (destCol > 0 && resolveGrid(row, col) != resolveGrid(destRow, destCol - 1)) {
                    pieces[destRow][destCol - 1].hp -= damage;
                }
                if (destRow < 5 && resolveGrid(row, col) != resolveGrid(destRow + 1, destCol)) {
                    pieces[destRow + 1][destCol].hp -= damage;
                }
                if (destCol < 2 && resolveGrid(row, col) != resolveGrid(destRow, destCol + 1)) {
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
        return false;
    }

    /**
     * After every piece has taken a move, a new turn starts
     * moved pieces can take moves again.
     */
    public void resetTurn() {
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 3; c++) {
                if (pieces[r][c] != null)
                    pieces[r][c].resetPiece();
            }
        }
    }

}
