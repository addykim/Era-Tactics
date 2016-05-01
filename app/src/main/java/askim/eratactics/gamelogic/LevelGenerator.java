package askim.eratactics.gamelogic;

import android.content.Context;

import com.orm.SugarRecord;

/**
 * Created by nunuloop on 4/19/16.
 */
public class LevelGenerator extends SugarRecord {

    private String name;
    private Piece[][] enemyPieces;
    private boolean cleared;

    public LevelGenerator() {

    }

    public LevelGenerator(Context c) {

        // 3 enemies
        setAllNull();
        name = "Level 1";
        enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
        enemyPieces[0][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
        enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
        this.save();

        // 4 enemies
        setAllNull();
        name = "Level 2";
        enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
        enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
        enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
        enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
        this.save();

        // 4 enemies
        setAllNull();
        name = "Level 3";
        enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
        enemyPieces[0][1] = new Piece(EnumFile.ClassEnum.BADTEETH);
        enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
        enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
        enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
        this.save();

        // 4 enemies
        setAllNull();
        name = "Level 4";
        enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
        enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
        enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
        enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
        enemyPieces[2][1] = new Piece(EnumFile.ClassEnum.BADTEETH);
        this.save();
    }

    public void setAllNull() {
        name = null;
        enemyPieces = new Piece[3][3];
    }

    public String getName() { return name; }

    public boolean getCleared() { return cleared; }
}
