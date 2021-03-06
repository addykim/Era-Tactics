package askim.eratactics.gamelogic;

import android.preference.EditTextPreference;
import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Piece extends SugarRecord {
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

    // TODO set public variables to private and create setters/getter

    /* This is the level that the piece is associated with */
    private LevelGenerator level;
    private int position;
    public int maxHp, hp, atk, def, mag, res, mrg, atr;
    public double agi;
    private boolean isPlayer;
    public boolean leader;
    // TODO does this get properly saved?
    /* This is used to store the arraylist of skills. setSkills must be called upon loading a piece */
    private int[] skillOrdinals = new int[6];

    private boolean hasMoved;
    private EnumFile.ClassEnum pieceClass;

    /* This is the actual arraylist of skills */
    @Ignore
    private ArrayList<EnumFile.SkillsEnum> skills;

    public Piece() {}

    public Piece(Adventurer adv, boolean isLeader) {
        position = adv.getPosition();
        level = null;
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
        pieceClass = adv.getAdventureClassAsEnum();
        Log.d(TAG, "The class of this piece is " + pieceClass);
    }

    // Generate generic enemies with 10 hp, 5 atk
    public Piece(EnumFile.ClassEnum enemyClass, LevelGenerator level) {
        this.level = level;
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
        saveSkillEnums();
        this.save();
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

    /* Called when round is reset */
    public void resetPiece() {
        hasMoved = false;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }

    /* Get the piece's class */
    public EnumFile.ClassEnum getPieceClass() {
        return pieceClass;
    }

    /* Sets the level that the piece belongs to. Only applies to enemies */
    public boolean setLevel(LevelGenerator level) {
        if (level != null) {
            this.level = level;
            this.save();
            return true;
        }
        return false;
    }

    public int getPosition() { return position; }

    public boolean setPosition(int position) {
        if (0 <= position && position <=8) {
            this.position = position;
            this.save();
        }
        return false;
    }

    /* This must be called when instantiating enemies in order to grab the store the values */
    public void setSkillEnums() {
        skills = new ArrayList<EnumFile.SkillsEnum>();
        for (int skill: skillOrdinals) {
            skills.add(EnumFile.SkillsEnum.values()[skill]);
        }
    }

    /* Saves the skills enum arraylist in a format okay to the database */
    public void saveSkillEnums() {
        int i = 0;
        for (EnumFile.SkillsEnum skill: skills) {
            skillOrdinals[i] = skill.ordinal();
        }
        this.save();
    }
}
