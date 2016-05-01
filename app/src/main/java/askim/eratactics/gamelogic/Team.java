package askim.eratactics.gamelogic;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by nunuloop on 3/24/16.
 */
public class Team extends SugarRecord {
    private final String TAG = "Team";

    /**
     * Array order for the team:
     * 0 1 2
     * 3 4 5
     * 6 7 8
     */

    private Adventurer[] adventurers;
    private ArrayList<Adventurer> teamMembers;
    private Adventurer leader;
    private int advCount;

    public Team() {
        adventurers = new Adventurer[9];
        teamMembers = new ArrayList<Adventurer>();
    }

    public boolean addTeamMember(Adventurer adv) {
        if (teamMembers.size() < 5 && !teamMembers.contains(adv)) {
            teamMembers.add(adv);
            Log.d(TAG, "Team now has " + teamMembers.size() + " members.");
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

    public ArrayList<Adventurer> getTeamMembers() { return teamMembers; }
}
