package askim.eratactics.gamelogic;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Adventurer extends SugarRecord {
    private static final String TAG = "Adventurer";
    public String name;
    private int lvl;
    public Equipment adventurerClass;
    // 0 = head, 1 = left hand, 2 = right hand, 3 = body
    private Equipment[] equipments = new Equipment[4];
    private String leaderSkillDescription;



    public Adventurer() {}

    public Adventurer(Equipment[] equips, String advName) {
        name = advName;

        // TODO: Right now we're assuming that the equipments passed in are the correct numbers and types. Later need to check these and throw exceptions if necessary.
        for (Equipment e : equips) {
            if (e.pos == 3) {
                Log.d(TAG, "Setting adventurer class as " + e.getName());
                adventurerClass = e;
            }
            else if (e.pos == 0) {
                Log.d(TAG, "Setting adventurer helmet as " + e.getName());
                equipments[0] = e;
            }
            else if (e.pos == 1) {
                if (equipments[1] == null) {
                    Log.d(TAG, "Setting adventurer left hand as " + e.getName());
                    equipments[1] = e;
                }
                else {
                    Log.d(TAG, "Setting adventurer right hand as " + e.getName());
                    equipments[2] = e;
                }
            }
            else {
                Log.d(TAG, "Setting adventurer armor as " + e.getName());
                equipments[3] = e;
            }
        }
        lvl = 1;
        leaderSkillDescription = "No Leader Equipment Selected.";
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


    public Equipment getEquipment(EnumFile.EquipmentSlots e) {
        return equipments[e.getPosition()];
    }
    public Equipment getEquipment(int equipmentPosition) {
        return equipments[equipmentPosition];
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
        int hp = (int)adventurerClass.getHp();
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                hp += equipments[i].getHp();
            }
        }
        if (isLeader)
            hp += 5;
        return hp;
    }

    public int getAtk(boolean isLeader, boolean[] equips) {
        int atk = (int)adventurerClass.getAtk();
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                atk += equipments[i].getAtk();
            }
        }
        if (isLeader)
            atk += 1;
        return atk;
    }

    public int getDef(boolean isLeader, boolean[] equips) {
        int def = (int)adventurerClass.getDef();
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                def += equipments[i].getDef();
            }
        }
        if (isLeader)
            def += 1;
        return def;
    }

    public int getMag(boolean isLeader, boolean[] equips) {
        int mag = (int)adventurerClass.getMag();
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                mag += equipments[i].getMag();
            }
        }
        if (isLeader)
            mag += 1;
        return mag;
    }

    public int getRes(boolean isLeader, boolean[] equips) {
        int res = (int)adventurerClass.getRes();
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                res += equipments[i].getRes();
            }
        }
        if (isLeader)
            res += 1;
        return res;
    }

    public int getMrg(boolean isLeader, boolean[] equips) {
        int mrg = (int)adventurerClass.getMrg();
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                mrg += equipments[i].getMrg();
            }
        }
        if (isLeader)
            mrg += 1;
        return mrg;
    }

    public int getAtr(boolean isLeader, boolean[] equips) {
        int atr = (int)adventurerClass.getAtr();
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                atr += equipments[i].getAtr();
            }
        }
        if (isLeader)
            atr += 0;
        return atr;
    }

    public double getAgi(boolean isLeader, boolean[] equips) {
        double agi = adventurerClass.getAgi();
        for (int i = 0; i < 4; i++) {
            if (equipments[i] != null && equips[i] == true) {
                agi += equipments[i].getAgi();
            }
        }
        if (isLeader)
            agi += 0;
        return agi;
    }

    public ArrayList<Equipment> availableEquipment(int pos, ArrayList<Equipment> allEquipment) {
        ArrayList<Equipment> results = new ArrayList<Equipment>();

        for (Equipment e : allEquipment) {
            if (!e.isEquipped() && e.isCompatible(adventurerClass.getClassName()) && e.pos == pos)
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
            leaderSkillDescription = name + " has " + equipments[pos].getName() + " as the leader skill!";
        else
            leaderSkillDescription = name + "\'s leader equipment " + equipments[pos].getName()
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

    public String getLeaderSkillDescription() {
        return leaderSkillDescription;
    }

    public Equipment getAdventurerClass() { return adventurerClass; }

    public String getAdventurerClassAsString() { return adventurerClass.getName(); }

    public int getAdventurerLevel() { return lvl; }

    public Equipment[] getAllEquipments() { return equipments; }

}
