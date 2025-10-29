package main.item.weapon;

public class ShortSword extends Weapon {
    public ShortSword() {
        this(1);
    }
    public ShortSword(double dropChance) {
        super("Shortsword of Boringness",
                "A completely unremarkable short sword. " +
                        "...Actually, this might just be a normal sword broken in half.",
                8,
                dropChance,
                4,
                1);
    }
}
