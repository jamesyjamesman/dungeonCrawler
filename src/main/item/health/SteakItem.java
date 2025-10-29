package main.item.health;

public class SteakItem extends HealthItem {
    public SteakItem(double dropChance) {
        super("Steak",
                "A perfectly cooked steak.",
                7,
                dropChance,
                10,
                4,
                9);
    }
}
