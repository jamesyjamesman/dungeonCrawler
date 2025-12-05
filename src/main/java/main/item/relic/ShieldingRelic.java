package main.item.relic;

import main.App;
import main.entity.Player;

import java.util.Random;

public class ShieldingRelic extends Relic {
    public ShieldingRelic() {
        this(1);
    }
    public ShieldingRelic(double dropChance) {
        super("Relic of Shielding",
                "A shimmering relic that gives a capped, but fairly constant source of shielding when near enemies.",
                dropChance,
                RelicID.SHIELDING);
    }

    @Override
    public String useRelic(Player player) {
        if (player.getAbsorption() < 5 && App.INSTANCE.getState() == App.State.PLAYER_TURN) {
            int absorptionAmount = new Random().nextInt(0, 3);
            if (absorptionAmount > 0) {
                player.addAbsorption(absorptionAmount);
                return "Your " + getName() + " gave you " + absorptionAmount + " levels of absorption!";
            }
        }
        return "";
    }
}
