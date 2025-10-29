package main.item.weapon;

public class Mace extends Weapon {
    public Mace() {
        this(1);
    }
    public Mace(double dropChance) {
        super("Rusty Mace",
                "A dilapidated old mace. You're surprised it still works!",
                12,
                dropChance,
                2,
                2);
    }
}