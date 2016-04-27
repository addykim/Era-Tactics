package askim.eratactics.gamelogic;

import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nunuloop on 4/26/16.
 */
public class PlayerAdventurers {
    private ArrayList<Adventurer> squad;
    private ArrayList<Equipment> inventory;
    private Team team1;
    private Team team2;

    public PlayerAdventurers(int playerId) {
        squad = new ArrayList<Adventurer>();
        inventory = new ArrayList<Equipment>();
        team1 = new Team();
        team2 = new Team();

        if (playerId == 0) {
            Adventurer villager1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.VILLAGER)});
            Adventurer apprentice1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.APPRENTICE)});
            Adventurer magician1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.MAGICIAN)});
            Adventurer archer1 = new Adventurer(new Equipment[]{new Equipment(EnumFile.ClassEnum.ARCHER)});
            squad.add(villager1);
            squad.add(apprentice1);
            squad.add(magician1);
            squad.add(archer1);

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
        }
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
