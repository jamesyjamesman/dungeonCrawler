package main.initialization;

import main.item.*;

import java.util.ArrayList;

public class ItemInit {
    static ArrayList<HealthItem> healthItemList = new ArrayList<>();
    static ArrayList<BuffItem> buffItemList = new ArrayList<>();
    static ArrayList<Item> itemList = new ArrayList<>();
    public static ArrayList<Item> itemInit() {
        ArrayList<HealthItem> healthItems = healthItemInit();
        ArrayList<BuffItem> buffItems = buffItemInit();
        itemList.addAll(healthItems);
        itemList.addAll(buffItems);
        return itemList;
    }
    public static ArrayList<HealthItem> healthItemInit() {
            HealthItem apple = new HealthItem();
            apple.setName("Apple");
            apple.setDescription("An apple you found in the cave system.\nIt doesn't seem too fresh.");
            apple.setRestorationRange(-1, 6);
            healthItemList.add(apple);

            HealthItem chocolate = new HealthItem();
            chocolate.setName("Torpedo Chocolate Bar™");
            chocolate.setDescription("A legendary chocolate bar thought to be long-lost.\nYou're salivating just looking at it.");
            chocolate.setRestorationRange(5, 15);
            chocolate.setAddedAbsorption(3);
            healthItemList.add(chocolate);

        return healthItemList;

    }

    public static ArrayList<BuffItem> buffItemInit() {
            BuffItem healthBuff = new HealthBuffItem();
            healthBuff.setBounds(1, 4);
            buffItemList.add(healthBuff);

            BuffItem damageBuff = new DamageBuffItem();
            damageBuff.setBounds(1, 3);
            buffItemList.add(damageBuff);

            BuffItem unBuff = new UnBuffItem();
            unBuff.setBounds(2, 7);
            buffItemList.add(unBuff);

            BuffItem inventoryBuff = new InventoryBuffItem();
            inventoryBuff.setBounds(1, 3);
            buffItemList.add(inventoryBuff);

            BuffItem relicBuff = new RelicPouchBuffItem();
            relicBuff.setBounds(0, 2);
            buffItemList.add(relicBuff);

        return buffItemList;
    }
}
