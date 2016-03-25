package askim.eratactics.gamelogic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

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
            }
        }
    }

    private void generateEnemies() {

    }

}
