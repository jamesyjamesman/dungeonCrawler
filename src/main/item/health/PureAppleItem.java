package main.item.health;

public class PureAppleItem extends HealthItem {
    public PureAppleItem(double dropChance) {
        super("Pure Apple",
                "The purified water cleansed any rot this apple once had. It looks perfect!",
                10,
                dropChance,
                1,
                5,
                10);
    }
}
