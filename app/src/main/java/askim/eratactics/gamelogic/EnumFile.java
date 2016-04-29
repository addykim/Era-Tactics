package askim.eratactics.gamelogic;

/**
 * Created by nunuloop on 3/22/16.
 */
public class EnumFile {
    public enum TurnStatus {
        CHARACTER,
        ENEMY,
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
        NOSKILL,
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
        BASIC_POTION,
        BASIC_ARMOR,
        BASIC_HELMET
    }

    // 0 = head, 1 = left hand, 2 = right hand, 3 = body
    public enum EquipmentSlots {
        HEAD (0),
        LEFT_HAND (1),
        RIGHT_HAND (2),
        BODY (3);

        private int position;
        EquipmentSlots(int pos) {
            position = pos;
        }

        public int getPosition() {
            return position;
        }
    }
}
