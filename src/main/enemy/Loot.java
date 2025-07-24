package main.enemy;

import main.item.Item;

import java.util.ArrayList;
//TODO: add slimy sword, slimy spear, and give gold drops to bosses
public class Loot {
    int gold;
    ArrayList<Item> items;

    public Loot() {
        this.items = new ArrayList<>();
        this.gold = 0;
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
