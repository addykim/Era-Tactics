package askim.eratactics.gamelogic;

/**
 * Created by nunuloop on 4/19/16.
 */
public class LevelGenerator {

    public static Piece[][] generate(int lvl) {
        Piece[][] enemyPieces = new Piece[3][3];

        switch (lvl) {
            case 1:
                // 3 enemies
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                break;
            case 2:
                // 4 enemies
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                break;
            case 3:
                // 4 enemies
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][1] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                break;
            case 4:
                // 4 enemies
                enemyPieces[0][0] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[0][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[1][1] = new Piece(EnumFile.ClassEnum.HORNEDFROG);
                enemyPieces[2][2] = new Piece(EnumFile.ClassEnum.BADTEETH);
                enemyPieces[2][1] = new Piece(EnumFile.ClassEnum.BADTEETH);
                break;
        }
        return enemyPieces;
    }
}
