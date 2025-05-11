import java.util.ArrayList;

public class ItemInit {
    static ArrayList<HealthItem> healthItemList = new ArrayList<>();
    static ArrayList<Item> itemList = new ArrayList<>();
    public static ArrayList<Item> itemInit() {
        ArrayList<HealthItem> healthItems = healthItemInit();
        itemList.addAll(healthItems);
        return itemList;
    }
    public static ArrayList<HealthItem> healthItemInit() {
            HealthItem apple = new HealthItem();
            apple.name = "Apple";
            apple.description = "An apple you found in the cave system. It doesn't seem too fresh.";
            apple.restorationRange[0] = -3;
            apple.restorationRange[1] = 5;
            healthItemList.add(apple);

            HealthItem chocolate = new HealthItem();
            chocolate.name = "Torpedo Chocolate Bar™";
            chocolate.description = "A legendary chocolate bar thought to be long-lost. You're salivating just looking at it.";
            chocolate.restorationRange[0] = 5;
            chocolate.restorationRange[1] = 15;
            chocolate.maxHealthChange = 3;
            healthItemList.add(chocolate);

        return healthItemList;

    }
}
