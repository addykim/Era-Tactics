package askim.eratactics.gamelogic;

/**
 * Created by nunuloop on 3/22/16.
 */
public class Equipment {
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
    public int pos;
    private EnumFile.SkillsEnum skill;
    private EnumFile.SkillsEnum leaderSkill;
    private boolean leaderSkillActivated;

    // making sure that the equipment goes with the adventurer's class
    public EnumFile.ClassEnum[] compatibleClasses;

    public Equipment() {
        stats = new double[]{3, 0, 0, 0, 0, 0, 0, 0};
        skill = EnumFile.SkillsEnum.FIREBALL;
        compatibleClasses = new EnumFile.ClassEnum[]{EnumFile.ClassEnum.KNIGHT};
        name = "SuperShield";
        pos = 1;
        leaderSkill = EnumFile.SkillsEnum.LIGHTNING;
        leaderSkillActivated = true;
    }

    public Equipment(EnumFile.ClassEnum className) {
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
    }

    public Equipment(EnumFile.Equipments equipment) {
        switch (equipment) {
            case BASIC_SWORD:
                stats = new double[]{0, 2, 1, 0, 0, 0, 1, 0};
                name = "Basic Sword";
                pos = 1;
                compatibleClasses = new EnumFile.ClassEnum[]{EnumFile.ClassEnum.FIGHTER, EnumFile.ClassEnum.KNIGHT};
                break;
            case BASIC_SHIELD:
                stats = new double[]{5, 1, 2, 0, 0, 0, 0, 0};
                name = "Basic Shield";
                pos = 1;
                compatibleClasses = new EnumFile.ClassEnum[]{EnumFile.ClassEnum.KNIGHT};
                break;
            case BASIC_ARROW:
                stats = new double[]{0, 1, 0, 1, 0, 0, 5, 0.1};
                name = "Basic Arrow";
                pos = 1;
                compatibleClasses = new EnumFile.ClassEnum[]{EnumFile.ClassEnum.ARCHER};
                break;
            case BASIC_WAND:
                stats = new double[]{2, 1, 0, 3, 1, 1, 4, 0.1};
                name = "Basic Wand";
                pos = 1;
                compatibleClasses = new EnumFile.ClassEnum[]{EnumFile.ClassEnum.MAGICIAN};
                break;
            case BASIC_POTION:
                stats = new double[]{0, 0, 0, 0, 0, 0, 0, 0};
                name = "Basic Potion";
                pos = 1;
                compatibleClasses = new EnumFile.ClassEnum[]{EnumFile.ClassEnum.MAGICIAN, EnumFile.ClassEnum.APPRENTICE};
                break;
        }    }


//GETTER METHODS:
    public EnumFile.SkillsEnum getSkill() {
        return skill;
    }

    public boolean isLeaderSkillActivated() {
        return leaderSkillActivated;
    }

    public EnumFile.SkillsEnum getLeaderSkill() {
        return leaderSkill;
    }

}
