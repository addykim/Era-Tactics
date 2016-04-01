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

    private boolean debugMode;

    public Team() {
        adventurers = new Adventurer[9];
        debugMode = false;
    }

    /* Creates a single leader */
    public Team(boolean debugMode) {
        adventurers = new Adventurer[9];
        debugMode = true;
        addAdventurer(new Adventurer(new Equipment[3]), 0, true);
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
