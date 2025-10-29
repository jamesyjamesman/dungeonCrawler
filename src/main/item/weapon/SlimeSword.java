package main.item.weapon;

public class SlimeSword extends Weapon {
    public SlimeSword() {
        this(1);
    }
    public SlimeSword(double dropChance) {
        super("Slimy Sword",
                "A now slime-covered sword from an adventurer before you.",
                12,
                dropChance,
                0,
                2
                );
    }
}
