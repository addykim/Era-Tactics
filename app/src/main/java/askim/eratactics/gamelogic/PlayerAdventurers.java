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
    private ArrayList<Adventurer> squad;
    private ArrayList<Equipment> inventory;
    private Team team1;
    private Team team2;

    public PlayerAdventurers(Context c) {
        squad = new ArrayList<Adventurer>();
        inventory = new ArrayList<Equipment>();
        team1 = new Team();
        team2 = new Team();

//        Adventurer.deleteAll(Adventurer.class);
//        Equipment.deleteAll(Equipment.class);
//        Team.deleteAll(Team.class);
        
//        if (playerId == 0) {
            Adventurer villager1 = new Adventurer(new Equipment(EnumFile.ClassEnum.VILLAGER), null, null, null, null, "Bob");
            villager1.save();
            Adventurer apprentice1 = new Adventurer(new Equipment(EnumFile.ClassEnum.APPRENTICE),
                    new Equipment(EnumFile.Equipments.BASIC_POTION), null, null, null, "BOB!");
            apprentice1.save();
            Adventurer magician1 = new Adventurer(new Equipment(EnumFile.ClassEnum.MAGICIAN),
                    new Equipment(EnumFile.Equipments.BASIC_WAND),
                    new Equipment(EnumFile.Equipments.BASIC_POTION), null, null, "Still Bob");
            magician1.save();
            Adventurer archer1 = new Adventurer(new Equipment(EnumFile.ClassEnum.ARCHER),
                    new Equipment(EnumFile.Equipments.BASIC_ARROW), null, null, null, "Uh.. Bob.");
            archer1.save();
            Adventurer magician2 = new Adventurer(new Equipment(EnumFile.ClassEnum.MAGICIAN), null, null, null, null, "Not Bob");
            magician2.save();
            Adventurer apprentice2 = new Adventurer(new Equipment(EnumFile.ClassEnum.APPRENTICE), null, null, null, null, "No More Bob.");
            apprentice2.save();
            
            team1.addTeamMember(villager1);
            team1.putAdventurer(villager1, 2, false);
            team1.addTeamMember(apprentice1);
            team1.putAdventurer(apprentice1, 6, false);
            team1.addTeamMember(magician1);
            team1.putAdventurer(magician1, 3, false);
            team1.addTeamMember(archer1);
            team1.putAdventurer(archer1, 5, false);
            team1.save();

//            squ.add(villager1);
//            squad.add(apprentice1);
//            squad.add(magician1);
//            squad.add(archer1);
//            squad.add(magician2);
//            squad.add(apprentice2);

            Equipment basicSword = new Equipment(EnumFile.Equipments.BASIC_SWORD);
            Equipment basicWand = new Equipment(EnumFile.Equipments.BASIC_WAND);
            Equipment basicHelmet = new Equipment(EnumFile.Equipments.BASIC_HELMET);
            Equipment basicShield = new Equipment(EnumFile.Equipments.BASIC_SHIELD);
            Equipment basicArrow = new Equipment(EnumFile.Equipments.BASIC_ARROW);
            Equipment basicArmor = new Equipment(EnumFile.Equipments.BASIC_ARMOR);
            Equipment basicPotion = new Equipment(EnumFile.Equipments.BASIC_POTION);
            inventory.add(basicArmor);
            inventory.add(basicArrow);
            inventory.add(basicHelmet);
            inventory.add(basicPotion);
            inventory.add(basicShield);
            inventory.add(basicWand);
            inventory.add(basicSword);
//        }
    }

    public ArrayList<Adventurer> getAllAdventurers() {
        return squad;
    }

    public ArrayList<Equipment> getInventory() {
        return inventory;
    }

    public void addAdventurer(Adventurer adv) {
        squad.add(adv);
    }

    public void addToInventory(Equipment equip) {
        inventory.add(equip);
    }

    public boolean deleteEquipment(Equipment equip) {
        if (inventory.contains(equip)) {
            inventory.remove(equip);
            return true;
        }
        else
            return false;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

}
