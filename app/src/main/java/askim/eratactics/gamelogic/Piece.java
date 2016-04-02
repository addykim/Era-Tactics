package askim.eratactics.gamelogic;

import java.util.ArrayList;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Piece {

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
    }

    // Generate generic enemies with 10 hp, 5 atk
    public Piece() {
        isPlayer = false;
        maxHp = 10;
        hp = 10;
        atk = 5;
        def = 2;
        mag = 2;
        res = 2;
        mrg = 2;
        atr = 2;
        agi = 0;
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
}
