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
        maxHp = adv.getHp(leader);
        hp = maxHp;
        atk = adv.getAtk(leader);
        def = adv.getDef(leader);
        mag = adv.getMag(leader);
        res = adv.getRes(leader);
        mrg = adv.getMrg(leader);
        atr = adv.getAtr(leader);
        agi = adv.getAgi(leader);
        pieceClass = adv.adventurerClass.className;
        Log.d(TAG, "The class of this piece is " + pieceClass);
    }

    // Generate generic enemies with 10 hp, 5 atk
    public Piece() {
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
        pieceClass = EnumFile.ClassEnum.ENEMY;
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
        Log.d(TAG, "Getting the class of this piece: " + pieceClass);

        return pieceClass;
    }
}
