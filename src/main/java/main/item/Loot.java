package main.item;

import main.entity.Player;
import main.item.relic.Relic;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

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
        SwingRenderer.appendLabelText("The enemy dropped " + this.getGold() + " gold!\n", false, ComponentType.LABEL_DESCRIPTION);
        for (int i = 0; i < this.getItems().size(); i++) {
            Item item = this.getItems().get(i);
            if (item instanceof Relic relic && player.hasRelic(relic)) {
                int halfBackGuarantee = relic.getValue() / 2;
                player.addGold(halfBackGuarantee);
                SwingRenderer.appendLabelText("You got an additional " + halfBackGuarantee + " gold!\n", false, ComponentType.LABEL_DESCRIPTION);
                continue;
            }
            if (new Random().nextDouble(0, 1) > item.getDropChance()) {
                continue;
            }
            Item itemClone = item.clone();
            SwingRenderer.appendLabelText("The enemy dropped a " + itemClone.getName() + "!\n", false, ComponentType.LABEL_DESCRIPTION);
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
