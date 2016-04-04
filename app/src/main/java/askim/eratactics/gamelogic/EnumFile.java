package askim.eratactics.gamelogic;

/**
 * Created by nunuloop on 3/22/16.
 */
public class EnumFile {
    public enum TurnStatus {
        CHARACTER,
        SKILL,
    }

    public enum SkillsEnum {
        FIREBALL,
        HEAL,
        LIGHTNING,
        MOVE,
        PUNCH,
        STRIKE,
        BLOCK,
        INVALID
    }

    public enum ClassEnum {
        BADTEETH,
        HORNEDFROG,
        VILLAGER,
        MAGICIAN,
        FIGHTER,
        ARCHER,
        KNIGHT,
        APPRENTICE
    }

    public enum EnemyClasses {

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
