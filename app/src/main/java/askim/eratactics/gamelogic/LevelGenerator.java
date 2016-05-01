package askim.eratactics.gamelogic;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * Created by nunuloop on 4/19/16.
 */
public class LevelGenerator extends SugarRecord {

    @Ignore
    public static final int NUM_LEVELS = 2;
    private String name;
    /* Piece[3][3] was turned into three arrays of int. This seems weird but it was dones o that we
     * we could use sugar record in order to be able to save each position.
     */
    int zero, one, two, three, four, five, six, seven, eight, nine;
    private boolean locked;
    private boolean cleared;

    public LevelGenerator() {}

    public LevelGenerator(Context c, int level) {
        StringBuffer sb = new StringBuffer();
        int id;
        locked = true;
        cleared = false;
        Piece piece1, piece2, piece3, piece4;

        switch(level) {
            case 1:
                // 3 enemies
                name = "Level 1";
                piece1 = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                id = piece1.getId().intValue();
                zero = id;
//                piece2 = new Piece(EnumFile.ClassEnum.HORNEDFROG);
//                id = piece2.getId().intValue();
//                row0[1] = id;
//                piece3 = new Piece(EnumFile.ClassEnum.HORNEDFROG);
//                id = piece3.getId().intValue();
//                row0[2] = id;
                locked = false;
                break;
            case 2:
                //4 enemies
                name = "Level 2";
                piece1 = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                id = piece1.getId().intValue();
                zero = id;
//                enemyPieces[0][0] = id;
//                piece2 = new Piece(EnumFile.ClassEnum.BADTEETH);
//                id = piece2.getId().intValue();
//                row0[2] = id;
//                enemyPieces[0][2] = id;
//                piece3 = new Piece(EnumFile.ClassEnum.HORNEDFROG);
//                id = piece3.getId().intValue();
//                row1[1] = id;
//                enemyPieces[1][1] = id;
//                piece4 = new Piece(EnumFile.ClassEnum.BADTEETH);
//                id = piece4.getId().intValue();
//                row2[2] = id;
//                enemyPieces[2][2] = id;
//                break;
//            case 3:
//                name = "Level 3";
//                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
//                enemyPieces[0][1] = new Piece(EnumFile.ClassEnum.BADTEETH);
//                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
//                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
//                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
//                break;
//            case 4:
//                name = "Level 4";
//                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
//                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
//                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
//                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
//                enemyPieces[2][1] = new Piece(EnumFile.ClassEnum.BADTEETH);
//                break;
        }
        // Saving the level created
        this.save();
    }

    public String getName() { return name; }

    public boolean getLocked() { return locked; }

    public void setLocked(boolean locked) { this.locked = locked; }

    public boolean getCleared() { return cleared; }

    public void setCleared(boolean cleared) { this.cleared = cleared; }

}
