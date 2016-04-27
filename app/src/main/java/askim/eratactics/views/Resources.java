package askim.eratactics.views;

import askim.eratactics.R;
import askim.eratactics.gamelogic.EnumFile;

/**
 * Created by addykim on 4/26/16.
 */
public class Resources {

    public static int getImageId(String advClass) {
        int id;
        switch (advClass.toUpperCase()) {
            case "VILLAGER":
                id = R.drawable.villager_normal;
                break;
            case "MAGICIAN":
                id = R.drawable.wizard_normal;
                break;
            case "KNIGHT":
                id = R.drawable.knight_normal;
                break;
            case "ARCHER":
                id = R.drawable.archer_normal;
                break;
            case "APPRENTICE":
                id = R.drawable.apprentice_normal;
                break;
            case "FIGHTER":
//              TODO change civilian to fighter
                id = R.drawable.civilian_normal;
                break;
            case "BADTEETH":
                id = R.drawable.enemy_badteeth;
                break;
            case "HORNEDFROG":
                id = R.drawable.enemy_hornedfrog;
                break;
            default:
                id = R.drawable.move;
        }
        return id;
    }

    public static int getAttackedImageId(String advClass) {
        int id;
        switch (advClass.toUpperCase()) {
            case "VILLAGER":
                id = R.drawable.villager_dmged;
                break;
            case "MAGICIAN":
                id = R.drawable.wizard_dmged;
                break;
            case "KNIGHT":
                id = R.drawable.knight_dmged;
                break;
            case "ARCHER":
                id = R.drawable.archer_dmged;
                break;
            case "APPRENTICE":
                id = R.drawable.apprentice_dmged;
                break;
            case "FIGHTER":
//              TODO change civilian to fighter
                id = R.drawable.civilian_dmged;
                break;
            case "BADTEETH":
                id = R.drawable.enemy_badteeth;
                break;
            case "HORNEDFROG":
                id = R.drawable.enemy_hornedfrog;
                break;
            default:
                id = R.drawable.move;
        }
        return id;
    }
}
