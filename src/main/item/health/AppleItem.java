package main.item.health;

public class AppleItem extends HealthItem {
    public AppleItem(double dropChance) {
        super("An apple you found in the cave system. It doesn't seem too fresh.",
                "Apple",
                3,
                dropChance,
                12,
                -1,
                6);
    }

}
