package main.item.weapon;

public class SlimeSpear extends Weapon {
    public SlimeSpear() {
        this(1);
    }
    public SlimeSpear(double dropChance) {
        super("Slimy Spear",
                "The slimy pole lets you grip it easier.",
                15,
                dropChance,
                0,
                3);
    }
}
