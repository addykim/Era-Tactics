package askim.eratactics.gamelogic;

import java.util.ArrayList;

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
    public ArrayList<Adventurer> teamMembers;
    public Adventurer leader;
    public int advCount;

    private boolean debugMode;

    public Team() {
        adventurers = new Adventurer[9];
        teamMembers = new ArrayList<Adventurer>();
        debugMode = false;
    }

    /* Creates a single leader */
    public Team(boolean debugMode) {
        adventurers = new Adventurer[9];
        teamMembers = new ArrayList<Adventurer>();
        debugMode = true;
        putAdventurer(new Adventurer(new Equipment[3]), 0, true);
    }

    public boolean addTeamMember(Adventurer adv) {
        if (teamMembers.size() < 5 && !teamMembers.contains(adv)) {
            teamMembers.add(adv);
            return true;
        }
        return false;
    }

    public boolean putAdventurer(Adventurer adv, int pos, boolean isLeader) {
        if (teamMembers.contains(adv)) {
            for (int i = 0; i < 9; i++) {
                if (adventurers[i] == adv) {
                    adventurers[i] = null;
                    advCount--;
                }
            }
            adventurers[pos] = adv;
            if (isLeader) {
                leader = adv;
            }
            advCount++;
            return true;
        }
        return false;
    }

    public void swapAdventurer(int from, int to) {
        if (adventurers[to] != null) {
            Adventurer temp = adventurers[to];
            adventurers[to] = adventurers[from];
            adventurers[from] = temp;
        }
        else {
            adventurers[to] = adventurers[from];
            adventurers[from] = null;
        }
    }

    public Adventurer getAdventurer(int pos) {
        return adventurers[pos];
    }

    public Adventurer getLeader() {
        return leader;
    }
}
