package askim.eratactics.gamelogic;

/**
 * Created by nunuloop on 3/24/16.
 */
public class Team {

    /**
     * Array order for the team:
     * 0 1 2
     * 3 4 5
     * 6 7 8
     */

    public Adventurer[] adventurers;
    public Adventurer leader;
    public int advCount;

    public Team() {
        adventurers = new Adventurer[9];
    }

    public void addAdventurer(Adventurer adv, int pos, boolean isLeader) {
        adventurers[pos] = adv;
        if (isLeader) {
            leader = adv;
        }
        advCount++;
    }

    public Adventurer getAdventurer(int pos) {
        return adventurers[pos];
    }

    public Adventurer getLeader() {
        return leader;
    }
}
