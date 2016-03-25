package askim.eratactics.gamelogic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.IdentityHashMap;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Board {

    private Piece[][] pieces;

    public Board(Team team) {
        pieces = new Piece[6][3];
        for (int i = 0; i < 9; i++) {
            Adventurer temp = team.getAdventurer(i);
            if (temp != null) {
                Piece p;
                if (team.getLeader() == temp) {
                    p = new Piece(temp, true);
                }
                else {
                    p = new Piece(temp, false);
                }
                pieces[i/3 + 3][i%3] = p;
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

    // returns 0 if nothing in grid, 1 if enemy is in grid, 2 if adventurer
    public int resolveGrid(int row, int col) {
        if (pieces[row][col] == null) {
            return 0;
        }
        else if (!pieces[row][col].getIsPlayer()) {
            return 1;
        }
        else if (pieces[row][col].getIsPlayer()) {
            return 2;
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
        int targetType; // 0 for self, 1 for enemy, 2 for allies, 3 for self and allies

        switch (skill) {
            case FIREBALL:
                range = 2;
                targetType = 1;
                break;
            case LIGHTNING:
                range = 4;
                targetType = 1;
                break;
            case HEAL:
                range = 3;
                targetType = 3;
                break;
            default:
                range = -1;
                targetType = -1;
                break;
        }

        ArrayList<Integer> targets = new ArrayList<Integer>();
        for (int i = 0; i < 18; i++) {
            int r = i / 3;
            int c = i % 3;
            if (pieces[r][c] != null) {
                if (Math.abs(r - row) + Math.abs(c - col) <= range) {
                    switch (targetType) {
                        case 0:
                            targets.add(row * 3 + col);
                            return targets;
                        case 1:
                            if (!pieces[r][c].getIsPlayer()) {
                                targets.add(i);
                            }
                            break;
                        case 2:
                            if (pieces[r][c].getIsPlayer() && (row * 3 + col != i)) {
                                targets.add(i);
                            }
                            break;
                        case 3:
                            if (pieces[r][c].getIsPlayer()) {
                                targets.add(i);
                            }
                            break;
                    }
                }
            }
        }

        return targets;
    }

}
