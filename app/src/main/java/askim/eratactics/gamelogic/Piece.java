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

    public int hp, atk, def, mag, res, mrg, atr;
    public double agi;
    private boolean isPlayer;
    public boolean leader;
    private ArrayList<EnumFile.SkillsEnum> skills;

    public Piece(Adventurer adv, boolean isLeader) {
        isPlayer = true;
        leader = isLeader;
        skills = adv.getSkills(isLeader);
        hp = adv.getHp(leader); // etc.
    }

    // Generate generic enemies with 10 hp, 5 atk
    public Piece() {
        isPlayer = false;
        hp = 10;
        atk = 5;
    }

    public boolean getIsPlayer() {
        return isPlayer;
    }

    public ArrayList<EnumFile.SkillsEnum> getSkills() {
        return skills;
    }
}
