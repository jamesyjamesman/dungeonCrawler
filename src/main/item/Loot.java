package main.item;

import main.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

//TODO: add slimy sword, slimy spear, and give gold drops to bosses
public class Loot {
    int gold;
    ArrayList<ItemBlueprint> items;

    public Loot() {
        this.items = new ArrayList<>();
        this.gold = 0;
    }

    public Loot(int gold) {
        this.gold = gold;
        this.items = new ArrayList<>();
    }

    public Loot(int gold, ArrayList<ItemBlueprint> items) {
        this.gold = gold;
        this.items = items;
    }

    public Loot(int gold, ItemBlueprint itemBlueprint) {
        this.gold = gold;
        this.items = new ArrayList<>();
        this.items.add(itemBlueprint);
    }

    public void dropLoot(JFrame frame, Player player) {
        player.addGold(this.getGold());
        for (int i = 0; i < this.getItems().size(); i++) {
            ItemBlueprint blueprint = this.getItems().get(i);
            if (new Random().nextDouble(0, 1) > blueprint.getDropChance()) {
                continue;
            }
            Item item = Item.itemFactory(blueprint.getID());
            player.itemPickup(frame, item);
        }
    }

    public void addItemBlueprint(ItemBlueprint itemBlueprint) {
        this.items.add(itemBlueprint);
    }
    public int getGold() {
        return this.gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public ArrayList<ItemBlueprint> getItems() {
        return this.items;
    }
}
