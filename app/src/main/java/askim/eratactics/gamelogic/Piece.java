package askim.eratactics.gamelogic;

import android.preference.EditTextPreference;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Piece {
    private final String TAG = "Piece Class";

    /**
     * ADDED UP STATS
     * hp = health points
     * atk = physical attack power
     * def = physical defense
     * mag = magic attack power
     * res = magic resistance power
     * mrg = moving range
     * atr = attack range
     * agi = agility, 0~1, possibility of dodging (either)
     */

    public int maxHp, hp, atk, def, mag, res, mrg, atr;
    public double agi;
    private boolean isPlayer;
    public boolean leader;
    private ArrayList<EnumFile.SkillsEnum> skills;
    private boolean hasMoved;
    private EnumFile.ClassEnum pieceClass;

    public Piece(Adventurer adv, boolean isLeader) {
        isPlayer = true;
        leader = isLeader;
        skills = adv.getSkills(isLeader);
        hasMoved = false;
        boolean[] includeEquipments = new boolean[]{true, true, true, true};
        maxHp = adv.getHp(leader, includeEquipments);
        hp = maxHp;
        atk = adv.getAtk(leader, includeEquipments);
        def = adv.getDef(leader, includeEquipments);
        mag = adv.getMag(leader, includeEquipments);
        res = adv.getRes(leader, includeEquipments);
        mrg = adv.getMrg(leader, includeEquipments);
        atr = adv.getAtr(leader, includeEquipments);
        agi = adv.getAgi(leader, includeEquipments);
        pieceClass = adv.adventurerClass.className;
        Log.d(TAG, "The class of this piece is " + pieceClass);
    }

    // Generate generic enemies with 10 hp, 5 atk
    public Piece(EnumFile.ClassEnum enemyClass) {
        isPlayer = false;
        hasMoved = false;
        maxHp = 10;
        hp = 10;
        atk = 5;
        def = 2;
        mag = 2;
        res = 2;
        mrg = 2;
        atr = 2;
        agi = 0;
        skills = new ArrayList<EnumFile.SkillsEnum>();
        skills.add(EnumFile.SkillsEnum.PUNCH);
        skills.add(EnumFile.SkillsEnum.MOVE);
        skills.add(EnumFile.SkillsEnum.HEAL);
        pieceClass = enemyClass;
    }

    public boolean getIsPlayer() {
        return isPlayer;
    }

    public ArrayList<EnumFile.SkillsEnum> getSkills() {
        return skills;
    }

    public void moved() {
        hasMoved = true;
    }

    public void resetPiece() {
        hasMoved = false;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    public EnumFile.ClassEnum getPieceClass() {
//        Log.d(TAG, "Getting the class of this piece: " + pieceClass);

        return pieceClass;
    }
}
