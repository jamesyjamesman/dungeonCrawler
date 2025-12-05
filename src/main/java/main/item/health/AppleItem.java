package main.item.health;

public class AppleItem extends HealthItem {
    public AppleItem() {
        this(1);
    }

    public AppleItem(double dropChance) {
        super("Apple",
                "An apple you found in the cave system. It doesn't seem too fresh.",
                3,
                dropChance,
                12,
                -1,
                6,
                0,
                true);
    }

}
