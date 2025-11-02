package main.initialization;

import main.item.Item;
import main.item.buff.*;
import main.item.health.*;
import main.item.weapon.*;

import java.util.ArrayList;

public class ItemInit {
    public static ArrayList<Item> itemInit() {
        ArrayList<Item> itemList = new ArrayList<>();

        itemList.add(new AppleItem());
        itemList.add(new ChocolateItem());
        itemList.add(new SteakItem());

        itemList.add(new HealthBuffItem());
        itemList.add(new DamageBuffItem());
        itemList.add(new UnBuffItem());
        itemList.add(new InventoryBuffItem());
        itemList.add(new RelicPouchBuffItem());

        itemList.add(new Mace());
        itemList.add(new ShortSword());
        itemList.add(new Wand());

        return itemList;
    }
}
