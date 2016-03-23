package askim.eratactics.gamelogic;

/**
 * Created by nunuloop on 3/22/16.
 */
public class Equipment {
    /**
     * STATS array index
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
    public EnumFile.SkillsEnum[] skills;
    public EnumFile.SkillsEnum leaderSkill;
    public boolean leaderSkillActivated;

    // making sure that the equipment goes with the adventurer's class
    public EnumFile.ClassEnum[] compatibleClasses;

    public Equipment() {
        stats = new double[]{3, 0, 0, 0, 0, 0, 0, 0};
        skills = new EnumFile.SkillsEnum[]{EnumFile.SkillsEnum.FIREBALL};
        compatibleClasses = new EnumFile.ClassEnum[]{EnumFile.ClassEnum.KNIGHT};
        name = "SuperShield";
        pos = 1;
        leaderSkill = EnumFile.SkillsEnum.LIGHTNING;
        leaderSkillActivated = true;
    }

}
