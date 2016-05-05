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
    private String name;
    private int lvl;
    private int position;
    private Equipment adventurerClass;
    // 0 = head, 1 = left hand, 2 = right hand, 3 = body
    private Equipment head;
    private Equipment left;
    private Equipment right;
    private Equipment body;
    private String leaderSkillDescription;

    public Adventurer() {
    }

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
     * @param e   - new equipment
     * @param pos - where to equip it
     * @return true - equipment is compatible with the class and successfully equipped
     * false - equipment not compatible with class
     */
    // TODO switch to classenum
    public boolean changeEquipment(Equipment e, int pos) {
        if (e.compatibleClasses.contains(adventurerClass) && 0 <= pos && pos <= 3) {
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

    public Equipment getHeadEquipment() { return head; }
    public Equipment getLeftEquipment() { return left; }
    public Equipment getRightEquipment() { return right; }
    public Equipment getBodyEquipment() { return body; }


    // Stats getters that do calculations according to adventurers' base stats,
    // equipments enhancements, and their leader status

    /**
     * The following 8 methods are stats getters that calculate according to adventurers' base
     * stats, equipments enhancements, and their leader status
     *
     * @param isLeader - whether the adventurer is a leader
     * @param equips   - a list of booleans telling if an equipment is in effect
     *                 [0] - head
     *                 [1] - left hand
     *                 [2] - right hand
     *                 [3] - body
     * @return
     */
    public int getHp(boolean isLeader, boolean[] equips) {
        int hp = (int) adventurerClass.getHp();
        if (head != null && equips[0] == true)
            hp += head.getHp();
        if (left != null & equips[1] == true)
            hp += left.getHp();
        if (right != null & equips[2] == true)
            hp += right.getHp();
        if (body != null & equips[3] == true)
            hp += body.getHp();
        if (isLeader)
            hp += 5;
        return hp;
    }

    public int getAtk(boolean isLeader, boolean[] equips) {
        int atk = (int) adventurerClass.getAtk();
        if (head != null && equips[0] == true)
            atk += head.getAtk();
        if (left != null & equips[1] == true)
            atk += left.getAtk();
        if (right != null & equips[2] == true)
            atk += right.getAtk();
        if (body != null & equips[3] == true)
            atk += body.getAtk();
        if (isLeader)
            atk += 1;
        return atk;
    }

    public int getDef(boolean isLeader, boolean[] equips) {
        int def = (int) adventurerClass.getDef();
        if (head != null && equips[0] == true)
            def += head.getDef();
        if (left != null & equips[1] == true)
            def += left.getDef();
        if (right != null & equips[2] == true)
            def += right.getDef();
        if (body != null & equips[3] == true)
            def += body.getDef();
        if (isLeader)
            def += 1;
        return def;
    }

    public int getMag(boolean isLeader, boolean[] equips) {
        int mag = (int) adventurerClass.getMag();
        if (head != null && equips[0] == true)
            mag += head.getMag();
        if (left != null & equips[1] == true)
            mag += left.getMag();
        if (right != null & equips[2] == true)
            mag += right.getMag();
        if (body != null & equips[3] == true)
            mag += body.getMag();
        if (isLeader)
            mag += 1;
        return mag;
    }

    public int getRes(boolean isLeader, boolean[] equips) {
        int res = (int) adventurerClass.getRes();
        if (head != null && equips[0] == true)
            res += head.getRes();
        if (left != null & equips[1] == true)
            res += left.getRes();
        if (right != null & equips[2] == true)
            res += right.getRes();
        if (body != null & equips[3] == true)
            res += body.getRes();
        if (isLeader)
            res += 1;
        return res;
    }

    public int getMrg(boolean isLeader, boolean[] equips) {
        int mrg = (int) adventurerClass.getMrg();
        if (head != null && equips[0] == true)
            mrg += head.getMrg();
        if (left != null & equips[1] == true)
            mrg += left.getMrg();
        if (right != null & equips[2] == true)
            mrg += right.getMrg();
        if (body != null & equips[3] == true)
            mrg += body.getMrg();
        if (isLeader)
            mrg += 1;
        return mrg;
    }

    public int getAtr(boolean isLeader, boolean[] equips) {
        int atr = (int) adventurerClass.getAtr();
        if (head != null && equips[0] == true)
            atr += head.getAtr();
        if (left != null & equips[1] == true)
            atr += left.getAtr();
        if (right != null & equips[2] == true)
            atr += right.getAtr();
        if (body != null & equips[3] == true)
            atr += body.getAtr();
        if (isLeader)
            atr += 0;
        return atr;
    }

    public double getAgi(boolean isLeader, boolean[] equips) {
        double agi = adventurerClass.getAgi();
        if (head != null && equips[0] == true)
            agi += head.getAgi();
        if (left != null & equips[1] == true)
            agi += left.getAgi();
        if (right != null & equips[2] == true)
            agi += right.getAgi();
        if (body != null & equips[3] == true)
            agi += body.getAgi();
        if (isLeader)
            agi += 0;
        return agi;
    }

    //TODO what is the purpose of this method?
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

    public String getAdventurerName() {
        return name;
    }

    public void setLeaderEquipment(EnumFile.EquipmentPos pos) {
        boolean hasLeaderSkill = false;
        StringBuffer skill = new StringBuffer();
        skill.append(name + " has ");
        if (pos == EnumFile.EquipmentPos.HEAD) {
            head.setLeaderEquipment(true);
            if (head.getLeaderSkill() != null) {
                hasLeaderSkill = true;
                skill.append(getHeadEquipment().getLeaderSkill());
            }
        } else if (pos == EnumFile.EquipmentPos.LEFT) {
            left.setLeaderEquipment(true);
            if (left.getLeaderSkill() != null) {
                hasLeaderSkill = true;
                skill.append(getLeftEquipment().getLeaderSkill());
            }
        } else if (pos == EnumFile.EquipmentPos.RIGHT) {
            right.setLeaderEquipment(true);
            if (right.getLeaderSkill() != null) {
                hasLeaderSkill = true;
                skill.append(getRightEquipment().getLeaderSkill());
            }
        } else if (pos == EnumFile.EquipmentPos.BODY) {
            body.setLeaderEquipment(body.isLeaderSkillActivated());
            if (body.getLeaderSkill() != null) {
                hasLeaderSkill = true;
                skill.append(getBodyEquipment().getLeaderSkill());
            }
        }

        if (hasLeaderSkill) {
            skill.append(" as the leader skill!");
            leaderSkillDescription = skill.toString();
        } else {
            leaderSkillDescription = "No Leader Skill";
        }
        this.save();
    }

    //GETTER METHODS:
    public ArrayList<EnumFile.SkillsEnum> getSkills(boolean isLeader) {
        ArrayList<EnumFile.SkillsEnum> skills = new ArrayList<EnumFile.SkillsEnum>();
        skills.add(EnumFile.SkillsEnum.MOVE);
        skills.add(EnumFile.SkillsEnum.PUNCH);

        if (head != null) {
            skills.add(head.getSkill());
            if (head.isLeaderSkillActivated() && isLeader)
                skills.add(head.getLeaderSkill());
        }

        if (left != null) {
            skills.add(left.getSkill());
            if (left.isLeaderSkillActivated() && isLeader)
                skills.add(left.getLeaderSkill());
        }

        if (right != null) {
            skills.add(right.getSkill());
            if (right.isLeaderSkillActivated() && isLeader)
                skills.add(right.getLeaderSkill());
        }

        if (body != null) {
            skills.add(body.getSkill());
            if (body.isLeaderSkillActivated() && isLeader)
                skills.add(body.getLeaderSkill());
        }


        return skills;
    }

    /* Return the leader skill description as a string */
    public String getLeaderSkillDescription() {
        return leaderSkillDescription;
    }


    /* Get the adventurer's equipment in some form */
    public Equipment getAdventurerClassAsEquipment() {
        return adventurerClass;
    }

    public EnumFile.ClassEnum getAdventureClassAsEnum() {
        return adventurerClass.getClassName();
    }

    public String getAdventurerClassAsString() {
        return adventurerClass.getName();
    }

    public int getAdventurerLevel() {
        return lvl;
    }

    public void setTeam(Team team) {
        this.team = team;
        this.save();
    }

    /* Gets the position of the character on a board */
    public int getPosition() {
        return position;
    }

    /* @Return: true if it successfully set position, */
    public boolean setPosition(int position) {
        if (0 <= position && position <= 8) {
            this.position = position;
            return true;
        }
        return false;
    }
}
