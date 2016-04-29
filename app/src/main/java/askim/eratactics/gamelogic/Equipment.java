package askim.eratactics.gamelogic;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by nunuloop on 3/22/16.
 */
public class Equipment implements Parcelable {
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
    public double[] stats;
    public String name;

    // 0 = head, 1 = hands, 2 = body, 3 = class
    // NOTE: pos in equipment is DIFFERENT than pos in Adventurer's equipment list, DO NOT MIX UP
    public int pos;
    private EnumFile.SkillsEnum skill;
    private EnumFile.SkillsEnum leaderSkill;
    public EnumFile.ClassEnum className;
    private boolean leaderSkillActivated;

    // making sure that the equipment goes with the adventurer's class
    public ArrayList<EnumFile.ClassEnum> compatibleClasses;
    private boolean equipped;

    public Equipment() {
        stats = new double[]{3, 0, 0, 0, 0, 0, 0, 0};
        skill = EnumFile.SkillsEnum.FIREBALL;
        compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
        compatibleClasses.add(EnumFile.ClassEnum.KNIGHT);
        name = "SuperShield";
        pos = 1;
        leaderSkill = EnumFile.SkillsEnum.LIGHTNING;
        leaderSkillActivated = false;
    }


    public Equipment(Parcel in) {
        readFromParcel(in);
    }

    public Equipment(EnumFile.ClassEnum className) {
        this.className = className;
        switch (className) {
            case VILLAGER:
                stats = new double[]{15, 5, 2, 3, 1, 2, 1, 0};
                name = "Villager";
                pos = 3;
                break;
            case FIGHTER:
                stats = new double[]{25, 8, 5, 3, 2, 3, 1, 0.2};
                name = "Fighter";
                pos = 3;
                break;
            case KNIGHT:
                stats = new double[]{25, 10, 8, 3, 2, 3, 1, 0.3};
                name = "Knight";
                pos = 3;
                break;
            case ARCHER:
                stats = new double[]{20, 8, 5, 3, 2, 3, 1, 0.2};
                name = "Archer";
                pos = 3;
                break;
            case MAGICIAN:
                stats = new double[]{20, 5, 3, 7, 5, 3, 2, 0.3};
                name = "Magician";
                pos = 3;
                break;
            case APPRENTICE:
                stats = new double[]{20, 5, 3, 5, 3, 2, 1, 0.1};
                name = "Apprentice";
                pos = 3;
                break;
        }
        leaderSkillActivated = false;
    }

    public Equipment(EnumFile.Equipments equipment) {
        switch (equipment) {
            case BASIC_SWORD:
                stats = new double[]{0, 2, 1, 0, 0, 0, 1, 0};
                name = "Basic Sword";
                pos = 1;
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.FIGHTER);
                compatibleClasses.add(EnumFile.ClassEnum.KNIGHT);
                skill = EnumFile.SkillsEnum.STRIKE;
                break;
            case BASIC_SHIELD:
                stats = new double[]{5, 1, 2, 0, 0, 0, 0, 0};
                name = "Basic Shield";
                pos = 1;
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.KNIGHT);
                skill = EnumFile.SkillsEnum.BLOCK;
                break;
            case BASIC_ARROW:
                stats = new double[]{0, 1, 0, 1, 0, 0, 5, 0.1};
                name = "Basic Arrow";
                pos = 1;
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.ARCHER);
                skill = EnumFile.SkillsEnum.FIREBALL;
                break;
            case BASIC_WAND:
                stats = new double[]{2, 1, 0, 3, 1, 1, 4, 0.1};
                name = "Basic Wand";
                pos = 1;compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.MAGICIAN);
                skill = EnumFile.SkillsEnum.LIGHTNING;
                leaderSkill = EnumFile.SkillsEnum.FIREBALL;
                break;
            case BASIC_POTION:
                stats = new double[]{0, 0, 0, 0, 0, 0, 0, 0};
                name = "Basic Potion";
                pos = 1;
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.APPRENTICE);
                compatibleClasses.add(EnumFile.ClassEnum.MAGICIAN);
                skill = EnumFile.SkillsEnum.HEAL;
                break;
            case BASIC_ARMOR:
                stats = new double[]{0, 0, 3, 0, 0, 0, 0, 0};
                name = "Basic Armor";
                pos = 2;
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.APPRENTICE);
                compatibleClasses.add(EnumFile.ClassEnum.MAGICIAN);
                skill = EnumFile.SkillsEnum.NOSKILL;
                break;
            case BASIC_HELMET:
                stats = new double[]{0, 0, 2, 0, 0, 0, 0, 0};
                name = "Basic Helmet";
                pos = 0;
                compatibleClasses = new ArrayList<EnumFile.ClassEnum>();
                compatibleClasses.add(EnumFile.ClassEnum.APPRENTICE);
                compatibleClasses.add(EnumFile.ClassEnum.MAGICIAN);
                skill = EnumFile.SkillsEnum.NOSKILL;
                break;
        }
        leaderSkillActivated = false;
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
    public EnumFile.SkillsEnum getSkill() {
        return skill;
    }

    public boolean isLeaderSkillActivated() {
        return leaderSkillActivated;
    }

    public EnumFile.SkillsEnum getLeaderSkill() {
        return leaderSkill;
    }

    public boolean isEquipped() {
        return equipped;
    }


    public void readFromParcel(Parcel in) {
//        stats = in.readDoubleArray();
//        public double[] stats;
        name = in.readString();
        pos = in.readInt();
//        skill = in.read
//        private EnumFile.SkillsEnum skill;
//        private EnumFile.SkillsEnum leaderSkill;
//        public EnumFile.ClassEnum className;
//        leaderSkillActivated = in.readInt();

        // making sure that the equipment goes with the adventurer's class
//        public ArrayList<EnumFile.ClassEnum> compatibleClasses;

//        private boolean equipped;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeArray();
        // TODO

    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public Equipment createFromParcel(Parcel in) {
                    return new Equipment(in);
                }

                public Equipment[] newArray(int size) {
                    return new Equipment[size];
                }
            };
}
