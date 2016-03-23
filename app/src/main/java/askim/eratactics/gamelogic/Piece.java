package askim.eratactics.gamelogic;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Piece {
    final boolean isPlayer;

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


    public Piece (Adventurer adv) {
        isPlayer = true;
        hp = adv.getHp(); // etc.
    }


}
