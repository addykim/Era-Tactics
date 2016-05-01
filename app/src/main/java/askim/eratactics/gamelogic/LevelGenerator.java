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

    public LevelGenerator(Context c, int level) {
        cleared = false;
        enemyPieces = new Piece[3][3];

        switch(level) {
            case 1:
                // 3 enemies
                name = "Level 1";
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                break;
            case 2:
                // 4 enemies
                name = "Level 2";
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                break;
            case 3:
                name = "Level 3";
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][1] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                break;
            case 4:
                name = "Level 4";
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[2][1] = new Piece(EnumFile.ClassEnum.BADTEETH);
                break;


        }
        this.save();
    }

    public String getName() { return name; }

    public boolean getCleared() { return cleared; }
}
