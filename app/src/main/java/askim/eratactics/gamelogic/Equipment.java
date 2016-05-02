package askim.eratactics.gamelogic;

import android.os.Parcel;
import android.os.Parcelable;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nunuloop on 3/22/16.
 */
public class Equipment extends SugarRecord {
    /**
     * STATS array index, stores EXTRA stats that equipments would give the equipped adventurer
     * 0 hp = health points
     * 1 atk = physical attack power
     * 2 def = physical defense
     * 3 mag = magic attack power
     * 4 res = magic resistance power
     * 5 mrg = moving range
     * 6 atr = attack range
     * 7 agi = agility, 0~1, possibility of dodging (either)
     */
    private double hp;
    private double atk;
    private double def;
    private double mag;
    private double res;
    private double mrg;
    private double atr;
    private double agi;
    private String name;
    private EnumFile.Equipments enumName;
    private EnumFile.EquipmentPos position;

    // 0 = head, 1 = hands, 2 = body, 3 = class
    // NOTE: pos in equipment is DIFFERENT than pos in Adventurer's equipment list, DO NOT MIX UP
//    public int pos;
    private EnumFile.SkillsEnum skill;
    private EnumFile.SkillsEnum leaderSkill;
    private EnumFile.ClassEnum className;
    private boolean leaderSkillActivated;

    // making sure that the equipment goes with the adventurer's class
    public List<EnumFile.ClassEnum> compatibleClasses;
    private boolean equipped;

    public Equipment() {
        hp = 3;
        atk = 0;
        def = 0;
        mag = 0;
        res = 0;
        mrg = 0;
        atr = 0;
        agi = 0;
        skill = EnumFile.SkillsEnum.FIREBALL;
        compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
        compatibleClasses.add(EnumFile.ClassEnum.KNIGHT);
        name = "SuperShield";
        position = EnumFile.EquipmentPos.LEFT;
//        pos = 1;
        leaderSkill = EnumFile.SkillsEnum.LIGHTNING;
        leaderSkillActivated = false;
        this.save();
    }

    public Equipment(EnumFile.ClassEnum className) {
        this.className = className;
        switch (className) {
            case VILLAGER:
                hp = 15;
                atk = 5;
                def = 2;
                mag = 3;
                res = 1;
                mrg = 2;
                atr = 1;
                agi = 0;
                name = "Villager";
                break;
            case FIGHTER:
                hp = 25;
                atk = 8;
                def = 5;
                mag = 3;
                res = 2;
                mrg = 3;
                atr = 1;
                agi = 0.2;
                name = "Fighter";
                break;
            case KNIGHT:
                hp = 25;
                atk = 10;
                def = 8;
                mag = 3;
                res = 2;
                mrg = 3;
                atr = 1;
                agi = 0.3;
                name = "Knight";
                break;
            case ARCHER:
                hp = 20;
                atk = 8;
                def = 5;
                mag = 3;
                res = 2;
                mrg = 3;
                atr = 1;
                agi = 0.2;
                name = "Archer";
                break;
            case MAGICIAN:
                hp = 20;
                atk = 5;
                def = 3;
                mag = 7;
                res = 5;
                mrg = 3;
                atr = 2;
                agi = 0.3;
                name = "Magician";
                break;
            case APPRENTICE:
                hp = 20;
                atk = 5;
                def = 3;
                mag = 5;
                res = 3;
                mrg = 2;
                atr = 1;
                agi = 0.1;
                name = "Apprentice";
                break;
        }
        position = EnumFile.EquipmentPos.CLASS;
        leaderSkillActivated = false;
        this.save();
    }

    public Equipment(EnumFile.Equipments equipment) {
        enumName = equipment;
        switch (equipment) {
            case BASIC_SWORD:
                hp = 0;
                atk = 2;
                def = 1;
                mag = 0;
                res = 0;
                mrg = 0;
                atr = 1;
                agi = 0;
                name = "Basic Sword";
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.FIGHTER);
                compatibleClasses.add(EnumFile.ClassEnum.KNIGHT);
                skill = EnumFile.SkillsEnum.STRIKE;
                break;
            case BASIC_SHIELD:
                hp = 5;
                atk = 1;
                def = 2;
                mag = 0;
                res = 0;
                mrg = 0;
                atr = 0;
                agi = 0;
                name = "Basic Shield";
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.KNIGHT);
                skill = EnumFile.SkillsEnum.BLOCK;
                break;
            case BASIC_ARROW:
                hp = 0;
                atk = 1;
                def = 0;
                mag = 1;
                res = 0;
                mrg = 0;
                atr = 5;
                agi = 0.1;
                name = "Basic Arrow";
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.ARCHER);
                skill = EnumFile.SkillsEnum.FIREBALL;
                break;
            case BASIC_WAND:
                hp = 2;
                atk = 1;
                def = 0;
                mag = 3;
                res = 1;
                mrg = 1;
                atr = 4;
                agi = 0.1;
                name = "Basic Wand";
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.MAGICIAN);
                skill = EnumFile.SkillsEnum.LIGHTNING;
                leaderSkill = EnumFile.SkillsEnum.FIREBALL;
                break;
            case BASIC_POTION:
                hp = 0;
                atk = 0;
                def = 0;
                mag = 0;
                res = 0;
                mrg = 0;
                atr = 0;
                agi = 0;
                name = "Basic Potion";
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.APPRENTICE);
                compatibleClasses.add(EnumFile.ClassEnum.MAGICIAN);
                skill = EnumFile.SkillsEnum.HEAL;
                break;
            case BASIC_ARMOR:
                hp = 0;
                atk = 0;
                def = 3;
                mag = 0;
                res = 0;
                mrg = 0;
                atr = 0;
                agi = 0;
                name = "Basic Armor";
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.APPRENTICE);
                compatibleClasses.add(EnumFile.ClassEnum.MAGICIAN);
                skill = EnumFile.SkillsEnum.NOSKILL;
                break;
            case BASIC_HELMET:
                hp = 0;
                atk = 0;
                def = 2;
                mag = 0;
                res = 0;
                mrg = 0;
                atr = 0;
                agi = 0;
                name = "Basic Helmet";
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.APPRENTICE);
                compatibleClasses.add(EnumFile.ClassEnum.MAGICIAN);
                skill = EnumFile.SkillsEnum.NOSKILL;
                break;
        }
        position = EnumFile.EquipmentPos.LEFT;
        leaderSkillActivated = false;
        this.save();
    }

    public boolean isCompatible(EnumFile.ClassEnum className) {
        if (compatibleClasses.contains(className))
            return true;
        return false;
    }

    public void setEquipped(boolean equip) {
        equipped = equip;
    }

    public void setLeaderEquipment(boolean leader) {
        leaderSkillActivated = leader;
    }

    /* Getter methods */
    public EnumFile.SkillsEnum getSkill() { return skill; }

    public boolean isLeaderSkillActivated() {
        return leaderSkillActivated;
    }

    public EnumFile.SkillsEnum getLeaderSkill() {
        return leaderSkill;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public EnumFile.Equipments getEnumName() { return enumName; }

    public EnumFile.ClassEnum getClassName() { return className; }

    public double getHp() { return hp; }
    public double getAtk() { return atk; }
    public double getDef() { return def; }
    public double getMag() { return mag; }
    public double getRes() { return res; }
    public double getMrg() { return mrg; }
    public double getAtr() { return atr; }
    public double getAgi() { return agi; }
    public String getName() { return name; }


}
