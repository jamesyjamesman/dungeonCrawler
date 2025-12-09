package main.item.armor;

public class LeatherTunic extends Armor {

    public LeatherTunic() {
        this(1);
    }
    public LeatherTunic(double dropChance) {
        super(
            "Leather Tunic",
            "A ripped old leather tunic. It barely offers any protection.",
            6,
            false,
            dropChance,
            4,
            1
        );
    }
}
