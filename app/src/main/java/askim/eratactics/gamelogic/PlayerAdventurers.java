package askim.eratactics.gamelogic;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import java.util.ArrayList;

import askim.eratactics.activities.SettingsActivity;

/**
 * Created by nunuloop on 4/26/16.
 */
public class PlayerAdventurers {

    public PlayerAdventurers(Context c) {
        /* Create new inventory */
        Equipment basicSword = new Equipment(EnumFile.Equipments.BASIC_SWORD);
        Equipment basicWand = new Equipment(EnumFile.Equipments.BASIC_WAND);
        Equipment basicHelmet = new Equipment(EnumFile.Equipments.BASIC_HELMET);
        Equipment basicShield = new Equipment(EnumFile.Equipments.BASIC_SHIELD);
        Equipment basicArrow = new Equipment(EnumFile.Equipments.BASIC_ARROW);
        Equipment basicArmor = new Equipment(EnumFile.Equipments.BASIC_ARMOR);
        Equipment basicPotion = new Equipment(EnumFile.Equipments.BASIC_POTION);
        Equipment basicPotion1 = new Equipment(EnumFile.Equipments.BASIC_POTION);


        /* Create team and players */
        Team team1 = new Team();
        team1.save();
        Team team2 = new Team();

        Adventurer villager1 = new Adventurer(new Equipment(EnumFile.ClassEnum.VILLAGER),
                null, null, null, null, "Bob");
        villager1.setTeam(team1);
        villager1.save();

        Adventurer apprentice1 = new Adventurer(new Equipment(EnumFile.ClassEnum.APPRENTICE),
                null, basicPotion, null, null, "BOB!");
        basicPotion.setAdventurer(apprentice1);
        apprentice1.setTeam(team1);
        apprentice1.save();

        Adventurer magician1 = new Adventurer(new Equipment(EnumFile.ClassEnum.MAGICIAN), null,
                basicWand, basicPotion1, null, "Still Bob");
        basicWand.setAdventurer(magician1);
        basicPotion1.setAdventurer(magician1);
        magician1.setTeam(team1);
        magician1.save();

        Adventurer archer1 = new Adventurer(new Equipment(EnumFile.ClassEnum.ARCHER), null,
                basicArrow, null, null, "Uh.. Bob.");
        basicArrow.setAdventurer(archer1);
        archer1.setTeam(team1);
        archer1.save();

        Adventurer magician2 = new Adventurer(new Equipment(EnumFile.ClassEnum.MAGICIAN),
                null, null, null, null, "Not Bob");
        magician2.setTeam(team2);
        magician2.save();

        Adventurer apprentice2 = new Adventurer(new Equipment(EnumFile.ClassEnum.APPRENTICE),
                null, null, null, null, "No More Bob.");
        apprentice2.setTeam(team2);
        apprentice2.save();

        team1.addTeamMember(villager1);
        team1.putAdventurer(villager1, 2, false);

        team1.addTeamMember(apprentice1);
        team1.putAdventurer(apprentice1, 6, false);

        team1.addTeamMember(apprentice2);
        team1.putAdventurer(apprentice1, 4, false);

        team1.addTeamMember(magician1);
        team1.putAdventurer(magician1, 3, false);

        team1.addTeamMember(archer1);
        team1.putAdventurer(archer1, 5, false);
    }

    // TODO replace with SQL query to delete equipment
    public boolean deleteEquipment(Equipment equip) {
//        if (inventory.contains(equip)) {
//            inventory.remove(equip);
//            return true;
//        }
//        else
            return false;
    }

}
