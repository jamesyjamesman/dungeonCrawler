package main.item.weapon;

public class FieryGreatsword extends Weapon {
    public FieryGreatsword() {
        this(1);
    }
    public FieryGreatsword(double dropChance) {
        super("Fiery Greatsword",
            "A magical flaming sword forged by a Wyrm.",
                99,
                dropChance,
                0,
                6);
    }
}
