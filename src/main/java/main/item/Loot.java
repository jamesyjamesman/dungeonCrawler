package main.item;

import java.util.ArrayList;

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

    public int getGold() {
        return this.gold;
    }
    public ArrayList<Item> getItems() {
        return this.items;
    }
}
