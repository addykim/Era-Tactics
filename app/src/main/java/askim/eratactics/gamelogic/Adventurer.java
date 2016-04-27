package askim.eratactics.gamelogic;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Adventurer {
    private static final String TAG = "Adventurer";
    public String name;
    public Equipment adventurerClass;
    // 0 = head, 1 = left hand, 2 = right hand, 3 = body
    public Equipment[] equipments = new Equipment[4];
    private String leaderSkillDiscription;

    public int lvl;

    public Adventurer(Equipment[] equips, String advName) {
        name = advName;

        // TODO: Right now we're assuming that the equipments passed in are the correct numbers and types. Later need to check these and throw exceptions if necessary.
        for (Equipment e : equips) {
            if (e.pos == 3) {
                Log.d(TAG, "Setting adventurer class as " + e.name);
                adventurerClass = e;
            }
            else if (e.pos == 0) {
                Log.d(TAG, "Setting adventurer helmet as " + e.name);
                equipments[0] = e;
            }
            else if (e.pos == 1) {
                if (equipments[1] == null) {
                    Log.d(TAG, "Setting adventurer left hand as " + e.name);
                    equipments[1] = e;
                }
                else {
                    Log.d(TAG, "Setting adventurer right hand as " + e.name);
                    equipments[2] = e;
                }
            }
            else {
                Log.d(TAG, "Setting adventurer armor as " + e.name);
                equipments[3] = e;
            }
        }

        lvl = 1;

        leaderSkillDiscription = "No Leader Equipment Selected.";
    }

    /**
     *
     * @param e - new equipment
     * @param pos - where to equip it
     * @return true - equipment is compatible with the class and successfully equipped
     *         false - equipment not compatible with class
     */
    public boolean changeEquipment (Equipment e, int pos) {
        if (e.compatibleClasses.contains(adventurerClass)) {
            equipments[pos].setEquipped(false);
            equipments[pos] = e;
            equipments[pos].setEquipped(true);
            return true;
        }
        return false;
    }


// Stats getters that do calculations according to adventurers' base stats,
// equipments enhancements, and their leader status

    /**
     * The following 8 methods are stats getters that calculate according to adventurers' base
     * stats, equipments enhancements, and their leader status
     * @param isLeader - whether the adventurer is a leader
     * @param equips - a list of booleans telling if an equipment is in effect
     *               [0] - head
     *               [1] - left hand
     *               [2] - right hand
     *               [3] - body
     * @return
     */
    public int getHp(boolean isLeader, boolean[] equips) {
        int hp = (int)adventurerClass.stats[0];
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                hp += equipments[i].stats[0];
            }
        }
        if (isLeader)
            hp += 5;
        return hp;
    }

    public int getAtk(boolean isLeader, boolean[] equips) {
        int atk = (int)adventurerClass.stats[1];
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                atk += equipments[i].stats[1];
            }
        }
        if (isLeader)
            atk += 1;
        return atk;
    }

    public int getDef(boolean isLeader, boolean[] equips) {
        int def = (int)adventurerClass.stats[2];
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                def += equipments[i].stats[2];
            }
        }
        if (isLeader)
            def += 1;
        return def;
    }

    public int getMag(boolean isLeader, boolean[] equips) {
        int mag = (int)adventurerClass.stats[3];
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                mag += equipments[i].stats[3];
            }
        }
        if (isLeader)
            mag += 1;
        return mag;
    }

    public int getRes(boolean isLeader, boolean[] equips) {
        int res = (int)adventurerClass.stats[4];
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                res += equipments[i].stats[4];
            }
        }
        if (isLeader)
            res += 1;
        return res;
    }

    public int getMrg(boolean isLeader, boolean[] equips) {
        int mrg = (int)adventurerClass.stats[5];
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                mrg += equipments[i].stats[5];
            }
        }
        if (isLeader)
            mrg += 1;
        return mrg;
    }

    public int getAtr(boolean isLeader, boolean[] equips) {
        int atr = (int)adventurerClass.stats[6];
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                atr += equipments[i].stats[6];
            }
        }
        if (isLeader)
            atr += 0;
        return atr;
    }

    public double getAgi(boolean isLeader, boolean[] equips) {
        double agi = adventurerClass.stats[7];
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                agi += equipments[i].stats[7];
            }
        }
        if (isLeader)
            agi += 0;
        return agi;
    }

    public ArrayList<Equipment> availableEquipment(int pos, ArrayList<Equipment> allEquipment) {
        ArrayList<Equipment> results = new ArrayList<Equipment>();

        for (Equipment e : allEquipment) {
            if (!e.isEquipped() && e.isCompatible(adventurerClass.className) && e.pos == pos)
                results.add(e);
        }

        return results;
    }

    public String getAdventurerName() { return name; }

    public void setLeaderEquipment(int pos) {
        for (Equipment e : equipments) {
            if (e.isLeaderSkillActivated())
                e.setLeaderEquipment(false);
        }
        equipments[pos].setLeaderEquipment(true);
        if (equipments[pos].getLeaderSkill() != null)
            leaderSkillDiscription = name + " has " + equipments[pos].name + " as the leader skill!";
        else
            leaderSkillDiscription = name + "\'s leader equipment " + equipments[pos].name
                                   + " does not have a learder skill :P ";
    }

//GETTER METHODS:
    public ArrayList<EnumFile.SkillsEnum> getSkills(boolean isLeader) {
        ArrayList<EnumFile.SkillsEnum> skills = new ArrayList<EnumFile.SkillsEnum>();
        skills.add(EnumFile.SkillsEnum.MOVE);
        skills.add(EnumFile.SkillsEnum.PUNCH);
        for (Equipment e : equipments) {
            if (e != null && e.pos != 3) {
                skills.add(e.getSkill());
                if (e.isLeaderSkillActivated() && isLeader) {
                    skills.add(e.getLeaderSkill());
                }
            }
        }
        return skills;
    }

    public String getLeaderSkillDiscription() {
        return leaderSkillDiscription;
    }

}
