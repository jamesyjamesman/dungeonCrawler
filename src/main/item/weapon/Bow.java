package main.item.weapon;

public class Bow extends Weapon {
    public Bow() {
        this(1);
    }
    public Bow(double dropChance) {
        super("Bow",
                "A very average bow. Don't question where the arrows come from.",
                8,
                dropChance,
                4,
                2);
    }
}
