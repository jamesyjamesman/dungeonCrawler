package main.item.relic;

import main.Main;
import main.entity.Player;
import main.room.Room;
import main.swing.SwingRenderer;

import java.util.Random;

public class ShieldingRelic extends Relic {
    public ShieldingRelic() {
        this(1);
    }
    public ShieldingRelic(double dropChance) {
        super("Relic of Shielding",
                "A shimmering relic that gives a constant source of shielding, but only up to a limited amount.",
                dropChance,
                RelicID.SHIELDING);
    }

    @Override
    public void useRelic(Player player, Room room) {
        if (player.getAbsorption() < 5) {
            int absorptionAmount = new Random().nextInt(1, 3);
            player.addAbsorption(absorptionAmount);
            SwingRenderer.addHealthText("Your Relic of Shielding gave you " + absorptionAmount + " point" + Main.pluralChecker(absorptionAmount) + " of absorption!");
        }
    }
}
