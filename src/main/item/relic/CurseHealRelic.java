package main.item.relic;

public class CurseHealRelic extends Relic {
    public CurseHealRelic() {
        setName("Relic of Cursed Healing");
        setType(RelicType.CURSE_HEAL);
        setDescription("This interesting relic inverts the twisted energies from cursed relics, healing you instead.");
        setCursed(true);
    }
}
