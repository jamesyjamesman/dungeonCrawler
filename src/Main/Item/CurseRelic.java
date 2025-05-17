package Main.Item;

import java.util.Random;

public class CurseRelic extends Relic {
    public CurseRelic() {
        this.name = "Relic of Curse Detection";
        this.description = "This relic allows you to sense the sinister energies that come off of cursed objects.";
        this.cursed = new Random().nextInt(2) == 0;
    }
}
