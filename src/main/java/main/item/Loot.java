package main.item;

import main.entity.Player;
import main.item.relic.Relic;

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

    public void dropLoot(Player player) {
        player.addGold(this.getGold());
        for (int i = 0; i < this.getItems().size(); i++) {
            Item item = this.getItems().get(i);
            if (item instanceof Relic relic && player.hasRelic(relic)) {
                int halfBackGuarantee = relic.getValue() / 2;
                player.addGold(halfBackGuarantee);
                continue;
            }
            if (new Random().nextDouble(0, 1) > item.getDropChance()) {
                continue;
            }
            Item itemClone = item.clone();
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
