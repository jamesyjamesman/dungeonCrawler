package main.item.relic;

import main.App;
import main.Main;
import main.entity.Player;
import main.swing.SwingRenderer;

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
    public void useRelic(Player player) {
        if (player.getAbsorption() < 5 && App.INSTANCE.getState() == App.State.PLAYER_TURN) {
            int absorptionAmount = new Random().nextInt(0, 3);
            if (absorptionAmount > 0) {
                player.addAbsorption(absorptionAmount);
                SwingRenderer.addHealthText("Your Relic of Shielding gave you " + absorptionAmount + " point" + Main.pluralChecker(absorptionAmount) + " of absorption!");
            }
        }
    }
}
