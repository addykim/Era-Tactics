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
    // 0 = head, 1,2 = hand, 3 = body
    public Equipment[] equipments = new Equipment[4];

    public int lvl;

    public Adventurer(Equipment[] equips) {
        name = "Bob";

        // TODO: Right now we're assuming that the equipments passed in are the correct numbers and types. Later need to check these and throw exceptions if necessary.
        for (Equipment e : equips) {
            if (e.pos == 3)
                adventurerClass = e;
            else if (e.pos == 0)
                equipments[0] = e;
            else if (e.pos == 1) {
                if (equipments[1] == null)
                    equipments[1] = e;
                else
                    equipments[2] = e;
            }
            else
                equipments[3] = e;
        }

        lvl = 1;
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
            equipments[pos] = e;
            return true;
        }
        return false;
    }

    /**
     * Stats getters that do calculations according to Adventurers' base stats,
     * equipment enhancements, and leader status
     */
    public int getHp(boolean isLeader) {
        int hp = (int)adventurerClass.stats[0];
        for (Equipment e : equipments) {
            if (e != null) {
                hp += e.stats[0];
            }
        }
        if (isLeader)
            hp += 5;
        return hp;
    }

    public int getAtk(boolean isLeader) {
        int atk = (int)adventurerClass.stats[1];
        for (Equipment e : equipments) {
            if (e != null) {
                atk += e.stats[1];
            }
        }
        if (isLeader)
            atk += 1;
        return atk;
    }

    public int getDef(boolean isLeader) {
        int def = (int)adventurerClass.stats[2];
        for (Equipment e : equipments) {
            if (e != null) {
                def += e.stats[2];
            }
        }
        if (isLeader)
            def += 1;
        return def;
    }

    public int getMag(boolean isLeader) {
        int mag = (int)adventurerClass.stats[3];
        for (Equipment e : equipments) {
            if (e != null) {
                mag += e.stats[3];
            }
        }
        if (isLeader)
            mag += 1;
        return mag;
    }

    public int getRes(boolean isLeader) {
        int res = (int)adventurerClass.stats[4];
        for (Equipment e : equipments) {
            if (e != null) {
                res += e.stats[4];
            }
        }
        if (isLeader)
            res += 1;
        return res;
    }

    public int getMrg(boolean isLeader) {
        int mrg = (int)adventurerClass.stats[5];
        for (Equipment e : equipments) {
            if (e != null) {
                Log.d(TAG, "not null");
                mrg += e.stats[5];
            } else {
                Log.d(TAG, "equipment is null");
            }
        }
        if (isLeader)
            mrg += 1;
        return mrg;
    }

    public int getAtr(boolean isLeader) {
        int atr = (int)adventurerClass.stats[6];
        for (Equipment e : equipments) {
            if (e != null) {
                atr += e.stats[6];
            }
        }
        if (isLeader)
            atr += 0;
        return atr;
    }

    public double getAgi(boolean isLeader) {
        double agi = adventurerClass.stats[7];
        for (Equipment e : equipments) {
            if (e != null) {
                agi += e.stats[7];
            }
        }
        if (isLeader)
            agi += 0;
        return agi;
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

}
