package askim.eratactics.views;

import android.util.Log;

import askim.eratactics.R;
import askim.eratactics.gamelogic.EnumFile;

/**
 * Created by addykim on 4/26/16.
 */
public class Resources {

    public static int getImageId(EnumFile.ClassEnum advClass) { return getImageId(advClass.toString()); }

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

    public static int getAttackedImageId(EnumFile.ClassEnum advClass) { return getImageId(advClass.toString()); }

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

    public static int getSkillImageId(EnumFile.SkillsEnum skill) { return getSkillImageId(skill.toString()); }

    public static int getSkillImageId(String skill) {
        int id;
        switch (skill.toUpperCase()) {
            case "MOVE":
                id = R.drawable.move;
                break;
            case "FIREBALL":
                id =  R.drawable.fireball;
                break;
            case "HEAL":
                id =  R.drawable.first_aid;
                break;
            case "LIGHTNING":
                id =  R.drawable.lightning;
                break;
            case "BLOCK":
                id =  R.drawable.shield;
                break;
            case "PUNCH":
                id =  R.drawable.punch;
                break;
            case "STRIKE":
                id =  R.drawable.melee;
                break;
            // TODO potion
    //                id =  R.drawable.potion;
    //                break;
            default:
                id =  R.drawable.wizard_dmged;
        }
        return id;
    }

    public static int getSkillSoundId(EnumFile.SkillsEnum skill) { return getSkillSoundId(skill.toString()); }

    public static int getSkillSoundId(String skill) {
        int id;
        switch (skill) {
            case "PUNCH":
                id = R.raw.punch;
                break;
            case "LIGHTNING":
                id = R.raw.explosion;
                break;
            default:
//                Log.d(TAG, "Have not made a case yet for this skill");
                id = R.raw.punch;
                // TODO add more sounds
        }
        return id;
    }
}
