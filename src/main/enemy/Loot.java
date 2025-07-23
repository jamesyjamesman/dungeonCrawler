package main.enemy;

import main.item.Item;

import java.util.ArrayList;

public class Loot {
    int gold;
    ArrayList<Item> items;
    public Loot() {
        this.items = new ArrayList<>();
        this.gold = 0;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }
    public int getGold() {
        return this.gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public ArrayList<Item> getItems() {
        return this.items;
    }
}
