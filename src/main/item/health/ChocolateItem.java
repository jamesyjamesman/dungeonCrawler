package main.item.health;

public class ChocolateItem extends HealthItem {
    public ChocolateItem(double dropChance) {
        super("Torpedo Chocolate Bar",
                "A legendary chocolate bar thought to be long-lost. You're salivating just looking at it.",
                10,
                dropChance,
                6,
                5,
                15,
                3);
    }
}
