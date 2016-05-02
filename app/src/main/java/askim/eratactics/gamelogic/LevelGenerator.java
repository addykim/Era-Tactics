package askim.eratactics.gamelogic;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nunuloop on 4/19/16.
 */
public class LevelGenerator extends SugarRecord {

    @Ignore
    public static final int NUM_LEVELS = 4;
    private String name;
    @Ignore
    private Piece[][] enemyPieces;
    private boolean locked;
    private boolean cleared;

    public LevelGenerator() {}

    public LevelGenerator(Context c, int level) {
        locked = true;
        cleared = false;
        enemyPieces = new Piece[3][3];
        switch(level) {
            case 1:
                // 3 enemies
                name = "Level 1";
                locked = false;
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG, this);
                enemyPieces[0][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG, this);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.HORNEDFROG, this);
                break;
            case 2:
                // 4 enemies
                name = "Level 2";
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG, this);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH, this);
                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG, this);
                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH, this);
                break;
            case 3:
                // 4 enemies
                name = "Level 3";
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG, this);
                enemyPieces[0][1] = new Piece(EnumFile.ClassEnum.BADTEETH, this);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH, this);
                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG, this);
                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH, this);
                break;
            case 4:
                // 4 enemies
                name = "Level 4";
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG, this);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH, this);
                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG, this);
                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH, this);
                enemyPieces[2][1] = new Piece(EnumFile.ClassEnum.BADTEETH, this);
                break;
        }
        // Saving the level created
        this.save();
    }

    public String getName() { return name; }

    public boolean getLocked() { return locked; }

    public void setLocked(boolean locked) { this.locked = locked; }

    public boolean getCleared() { return cleared; }

    public void setCleared(boolean cleared) { this.cleared = cleared; }

    /* Returns the enemy pieces as 2d array */
    public Piece[][] getEnemyPieces() {
        if (enemyPieces == null) {
            if (setEnemyPieces()) {
                return enemyPieces;
            } else {
                throw new IllegalArgumentException();
            }
        }
//        throw new IllegalArgumentException();
        return null;
    }

    /* This needs to be called whenever "LevelGenerator.findbyId" is called */
    public boolean setEnemyPieces() {
        ArrayList<Piece> list = (ArrayList) Piece.find(Piece.class, "level = ?", getId().toString());
        if (list != null) {
            Piece[][] temp = new Piece[3][3];
            for (Piece piece : list) {
                int pos = piece.getPosition();
                temp[pos / 3][pos % 3] = piece;
            }
            return true;
        }
        return false;
    }
}
