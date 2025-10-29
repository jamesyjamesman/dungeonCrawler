package main.item.relic;

public class CurseHealRelic extends Relic {
    public CurseHealRelic() {
        this(1);
    }
    public CurseHealRelic(double dropChance) {
        super("Relic of Cursed Healing",
            "This interesting relic inverts the twisted energies from cursed relics, healing you instead.",
            dropChance,
            RelicID.CURSE_HEAL);
        setCursed(true);
    }
}
