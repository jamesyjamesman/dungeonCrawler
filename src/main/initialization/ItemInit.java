package main.initialization;

import main.item.Item;
import main.item.buff.*;
import main.item.health.AppleItem;
import main.item.health.ChocolateItem;
import main.item.health.HealthItem;
import main.item.health.SteakItem;
import main.item.weapon.ShortSword;
import main.item.weapon.Weapon;

import java.util.ArrayList;

public class ItemInit {
    static ArrayList<HealthItem> healthItemList = new ArrayList<>();
    static ArrayList<BuffItem> buffItemList = new ArrayList<>();
    static ArrayList<Weapon> weaponList = new ArrayList<>();
    static ArrayList<Item> itemList = new ArrayList<>();
    public static ArrayList<Item> itemInit() {
        ArrayList<HealthItem> healthItems = healthItemInit();
        ArrayList<BuffItem> buffItems = buffItemInit();
        ArrayList<Weapon> weapons = weaponInit();
        itemList.addAll(healthItems);
        itemList.addAll(buffItems);
        itemList.addAll(weapons);
        return itemList;
    }
    public static ArrayList<HealthItem> healthItemInit() {
            HealthItem apple = new AppleItem();
            healthItemList.add(apple);

            HealthItem chocolate = new ChocolateItem();
            healthItemList.add(chocolate);

            HealthItem steak = new SteakItem();
            healthItemList.add(steak);

        return healthItemList;

    }

    public static ArrayList<BuffItem> buffItemInit() {
            BuffItem healthBuff = new HealthBuffItem();
            buffItemList.add(healthBuff);

            BuffItem damageBuff = new DamageBuffItem();
            buffItemList.add(damageBuff);

            BuffItem unBuff = new UnBuffItem();
            buffItemList.add(unBuff);

            BuffItem inventoryBuff = new InventoryBuffItem();
            buffItemList.add(inventoryBuff);

            BuffItem relicBuff = new RelicPouchBuffItem();
            buffItemList.add(relicBuff);

        return buffItemList;
    }

    public static ArrayList<Weapon> weaponInit() {
        weaponList.add(new ShortSword());

        return weaponList;
    }
}
