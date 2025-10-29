package main.item.relic;

public class CurseDetectionRelic extends Relic {
    public CurseDetectionRelic() {
        this(1);
    }
    public CurseDetectionRelic(double dropChance) {
        super("Relic of Curse Detection",
                "This relic allows you to sense the sinister energies that come off of cursed objects.",
                dropChance,
                RelicID.CURSE_DETECTION);
    }
}
