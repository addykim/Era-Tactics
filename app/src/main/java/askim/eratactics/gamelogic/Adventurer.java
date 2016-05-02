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

    /* Defines which team this adventurer is a part of */
    private Team team;
    public String name;
    private int lvl;
    public Equipment adventurerClass;
    // 0 = head, 1 = left hand, 2 = right hand, 3 = body
    private Equipment head;
    private Equipment left;
    private Equipment right;
    private Equipment body;
    private String leaderSkillDescription;

    public Adventurer() {}

    public Adventurer(Equipment advClass, Equipment head, Equipment left, Equipment right, Equipment body, String advName) {
        name = advName;
        adventurerClass = advClass;
        this.head = head;
        this.left = left;
        this.right = right;
        this.body = body;
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
    // TODO switch to classenum
    public boolean changeEquipment (Equipment e, int pos) {
        if (e.compatibleClasses.contains(adventurerClass) && 0 <= pos && pos <=3) {
            if (pos == 0) {
                head.setEquipped(false);
                head = e;
                head.setEquipped(true);
            } else if (pos == 1) {
                left.setEquipped(false);
                left = e;
                left.setEquipped(true);
            } else if (pos == 2) {
                right.setEquipped(false);
                right = e;
                right.setEquipped(true);
            } else if (pos == 3) {
                body.setEquipped(false);
                body = e;
                body.setEquipped(true);
            }
            return true;
        }
        return false;
    }

    public Equipment getEquipment(EnumFile.EquipmentPos pos) {
        Equipment e = null;
        switch(pos) {
            case HEAD:
                e = head;
                break;
            case LEFT:
                e = left;
            case RIGHT:
                e = right;
            case BODY:
                e = body;
            default:
                Log.d(TAG, "Invalid equipment position");
        }
        return e;
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
        if (head != null && equips[0] == true)
            hp+=head.getHp();
        if (left != null & equips[1] == true)
            hp+=left.getHp();
        if (right != null & equips[2] == true)
            hp+=right.getHp();
        if (body != null & equips[3] == true)
            hp+=body.getHp();
        if (isLeader)
            hp += 5;
        return hp;
    }

    public int getAtk(boolean isLeader, boolean[] equips) {
        int atk = (int)adventurerClass.getAtk();
        if (head != null && equips[0] == true)
            atk+=head.getAtk();
        if (left != null & equips[1] == true)
            atk+=left.getAtk();
        if (right != null & equips[2] == true)
            atk+=right.getAtk();
        if (body != null & equips[3] == true)
            atk+=body.getAtk();
        if (isLeader)
            atk += 1;
        return atk;
    }

    public int getDef(boolean isLeader, boolean[] equips) {
        int def = (int)adventurerClass.getDef();
        if (head != null && equips[0] == true)
            def+=head.getHp();
        if (left != null & equips[1] == true)
            def+=left.getHp();
        if (right != null & equips[2] == true)
            def+=right.getHp();
        if (body != null & equips[3] == true)
            def+=body.getHp();
        if (isLeader)
            def += 1;
        return def;
    }

    public int getMag(boolean isLeader, boolean[] equips) {
        int mag = (int)adventurerClass.getMag();
        for (int i = 0; i < 4; i++) {
//            if (equipments[i] != null && equips[i] == true) {
//                mag += equipments[i].getMag();
//            }
        }
        if (isLeader)
            mag += 1;
        return mag;
    }

    public int getRes(boolean isLeader, boolean[] equips) {
        int res = (int)adventurerClass.getRes();
        if (head != null && equips[0] == true)
            res+=head.getRes();
        if (left != null & equips[1] == true)
            res+=left.getRes();
        if (right != null & equips[2] == true)
            res+=right.getRes();
        if (body != null & equips[3] == true)
            res+=body.getRes();
        if (isLeader)
            res += 1;
        return res;
    }

    public int getMrg(boolean isLeader, boolean[] equips) {
        int mrg = (int)adventurerClass.getMrg();
        for (int i = 0; i < 4; i++) {
//            if (equipments[i] != null && equips[i] == true) {
//                mrg += equipments[i].getMrg();
//            }
        }
        if (isLeader)
            mrg += 1;
        return mrg;
    }

    public int getAtr(boolean isLeader, boolean[] equips) {
        int atr = (int)adventurerClass.getAtr();
        for (int i = 0; i < 4; i++) {
//            if (equipments[i] != null && equips[i] == true) {
//                atr += equipments[i].getAtr();
//            }
        }
        if (isLeader)
            atr += 0;
        return atr;
    }

    public double getAgi(boolean isLeader, boolean[] equips) {
        double agi = adventurerClass.getAgi();
        if (head != null && equips[0] == true)
            agi+=head.getAgi();
        if (left != null & equips[1] == true)
            agi+=left.getAgi();
        if (right != null & equips[2] == true)
            agi+=right.getAgi();
        if (body != null & equips[3] == true)
            agi+=body.getAgi();
        if (isLeader)
            agi += 0;
        return agi;
    }

    public ArrayList<Equipment> availableEquipment(int pos, ArrayList<Equipment> allEquipment) {
        ArrayList<Equipment> results = new ArrayList<Equipment>();
//
//        for (Equipment e : allEquipment) {
//            if (!e.isEquipped() && e.isCompatible(adventurerClass.getClassName()) && e.pos == pos)
//                results.add(e);
//        }
//
        return results;
    }

    public String getAdventurerName() { return name; }

    public void setLeaderEquipment(EnumFile.EquipmentPos pos) {
        if (head.isLeaderSkillActivated())
            head.setLeaderEquipment(false);
        if (left.isLeaderSkillActivated())
            left.setLeaderEquipment(false);
        if (right.isLeaderSkillActivated())
            right.setLeaderEquipment(false);
        if (body.isLeaderSkillActivated())
            body.setLeaderEquipment(false);
//
//
//        equipments[pos].setLeaderEquipment(true);
//        if (equipments[pos].getLeaderSkill() != null)
//            leaderSkillDescription = name + " has " + equipments[pos].getName() + " as the leader skill!";
//        else
//            leaderSkillDescription = name + "\'s leader equipment " + equipments[pos].getName()
//                    + " does not have a learder skill :P ";
    }

    //GETTER METHODS:
    public ArrayList<EnumFile.SkillsEnum> getSkills(boolean isLeader) {
        ArrayList<EnumFile.SkillsEnum> skills = new ArrayList<EnumFile.SkillsEnum>();
        skills.add(EnumFile.SkillsEnum.MOVE);
        skills.add(EnumFile.SkillsEnum.PUNCH);
//        for (Equipment e : equipments) {
//            if (e != null && e.pos != 3) {
//                skills.add(e.getSkill());
//                if (e.isLeaderSkillActivated() && isLeader) {
//                    skills.add(e.getLeaderSkill());
//                }
//            }
//        }
        return skills;
    }

    /* Return the leader skill description as a string */
    public String getLeaderSkillDescription() { return leaderSkillDescription; }


    /* Get the adventurer's equipment in some form */
    public Equipment getAdventurerClassAsEquipment() { return adventurerClass; }
    public EnumFile.ClassEnum getAdventureClassAsEnum() { return adventurerClass.getClassName(); }
    public String getAdventurerClassAsString() { return adventurerClass.getName(); }

    public int getAdventurerLevel() { return lvl; }

    public void setTeam(Team team) { this.team = team; this.save(); }
}
