package main.item.weapon;

public class Wand extends Weapon {
    public Wand() {
        this(1);
    }
    public Wand(double dropChance) {
        super("Broken Wand",
                "A wand not capable of doing much more than shooting out short-lived sparks.",
                4,
                dropChance,
                5,
                1);
    }
}
