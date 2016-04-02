package askim.eratactics.gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Adventurer {
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

    public void changeEquipment (Equipment e, int pos) {
        equipments[pos] = e;
    }

    /**
     * Stats getters that do calculations according to Adventurers' base stats,
     * equipment enhancements, and leader status
     */
    public int getHp(boolean isLeader) {
        int hp = 0;
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
        int atk = 0;
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
        int def = 0;
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
        int mag = 0;
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
        int res = 0;
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
        int mrg = 0;
        for (Equipment e : equipments) {
            if (e != null) {
                mrg += e.stats[5];
            }
        }
        if (isLeader)
            mrg += 1;
        return mrg;
    }

    public int getAtr(boolean isLeader) {
        int atr = 0;
        for (Equipment e : equipments) {
            if (e != null) {
                atr += e.stats[5];
            }
        }
        if (isLeader)
            atr += 0;
        return atr;
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
