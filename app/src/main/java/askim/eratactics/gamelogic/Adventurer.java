package askim.eratactics.gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nunuloop on 3/10/16.
 */
public class Adventurer {
    public String name;
    public Equipment adventurerClass;
    // 0 = head, 1,2 = hand, 3 = body
    public Equipment[] equipments = new Equipment[4];

    public int lvl;
    public boolean leader;

    public Adventurer(Equipment[] equips) {
        name = "Bob";

        // TODO: Right now we're assuming that the equipments passed in are the correct numbers and types. Later need to check these and throw exceptions if necessary.
        for (Equipment e : equips) {
            if (e.pos == 3)
                adventurerClass = e;
            else if (e.pos == 0)
                equipments[0] = e;
            else if (e.pos == 1) {
                if (equipments[1] == null)
                    equipments[1] = e;
                else
                    equipments[2] = e;
            }
            else
                equipments[3] = e;
        }

        lvl = 1;
        leader = true;
    }

    public void changeEquipment (Equipment e, int pos) {
        equipments[pos] = e;
    }

    // getters that do calculations
    public int getHp() {
        return 0;
    }

}
