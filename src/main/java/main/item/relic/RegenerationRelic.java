package main.item.relic;

import main.App;
import main.entity.Player;

import java.util.Random;

public class RegenerationRelic extends Relic {
    public RegenerationRelic() {
        this(1);
    }
    public RegenerationRelic(double dropChance) {
        super("Relic of Regeneration",
                "This mystical artifact will heal you on occasion.",
                dropChance,
                RelicID.REGENERATION);
    }

    @Override
    public void useRelic(Player player) {
        if (App.INSTANCE.getState() != App.State.ROOM_END) {
            return;
        }
        if (new Random().nextInt(3) == 0) {
        int amountHealed = player.heal(new Random().nextInt(1,5));
        if (amountHealed != 0) {
            }
        }
    }
}
