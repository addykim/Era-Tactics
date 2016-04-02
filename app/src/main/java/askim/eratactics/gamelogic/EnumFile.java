package askim.eratactics.gamelogic;

/**
 * Created by nunuloop on 3/22/16.
 */
public class EnumFile {
    public enum SkillsEnum {
        FIREBALL,
        HEAL,
        LIGHTNING,
        MOVE,
        PUNCH
    }

    public enum ClassEnum {
        VILLAGER,
        MAGICIAN,
        FIGHTER,
        ARCHER,
        KNIGHT,
        APPRENTICE
    }

    public enum TargetType {
        SELF,
        ENEMY,
        ALLY,
        SELF_ALLY,
        ENEMY_EMPTY,
        EMPTY
    }

    public enum Equipments {
        BASIC_WAND,
        BASIC_SWORD,
        BASIC_SHIELD,
        BASIC_ARROW,
        BASIC_POTION
    }
}
