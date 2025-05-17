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
            apple.setRestorationRange(-3, 5);
            healthItemList.add(apple);

            HealthItem chocolate = new HealthItem();
            chocolate.setName("Torpedo Chocolate Bar™");
            chocolate.setDescription("A legendary chocolate bar thought to be long-lost.\nYou're salivating just looking at it.");
            chocolate.setRestorationRange(5, 15);
            chocolate.setMaxHealthChange(3);
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

        return buffItemList;
    }
}
