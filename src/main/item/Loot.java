package main.item;

import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Loot {
    int gold;
    ArrayList<Item> items;

    public Loot() {
        this.items = new ArrayList<>();
        this.gold = 0;
    }

    public Loot(int gold) {
        this.gold = gold;
        this.items = new ArrayList<>();
    }

    public Loot(int gold, ArrayList<Item> items) {
        this.gold = gold;
        this.items = items;
    }

    public Loot(int gold, Item item) {
        this.gold = gold;
        this.items = new ArrayList<>();
        this.items.add(item);
    }

    public void dropLoot(JFrame frame, Player player) {
        player.addGold(this.getGold());
        SwingRenderer.appendMainLabelText("The enemy dropped " + this.getGold() + " gold!\n", false);
        for (int i = 0; i < this.getItems().size(); i++) {
            Item item = this.getItems().get(i);
            if (new Random().nextDouble(0, 1) > item.getDropChance()) {
                continue;
            }
            Item itemClone = item.clone();
            SwingRenderer.appendMainLabelText("The enemy dropped a " + itemClone.getName() + "!\n", false);
            player.itemPickup(itemClone);
        }
    }

    public int getGold() {
        return this.gold;
    }
    public ArrayList<Item> getItems() {
        return this.items;
    }
}
