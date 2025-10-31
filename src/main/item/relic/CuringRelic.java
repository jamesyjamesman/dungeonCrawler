package main.item.relic;

import main.App;
import main.Statuses;
import main.entity.Player;
import main.swing.SwingRenderer;

import java.util.Random;

public class CuringRelic extends Relic {
    public CuringRelic() {
        this(1);
    }
    public CuringRelic(double dropChance) {
        super("Beghold's Infinite Exilir of Curing",
                "A suspicious bottle. It smells like cherries. If you take a swig, the level of red liquid doesn't go down.",
                dropChance,
                RelicID.CURE);
        setCursed(false);
    }

    @Override
    public void useRelic(Player player) {
        if (!(App.INSTANCE.getState() == App.State.ROOM_END || App.INSTANCE.getState() == App.State.PLAYER_TURN)) {
            return;
        }
        Statuses playerStatuses = player.getCurrentStatuses();
        int totalStatusCount = playerStatuses.getPoison() + playerStatuses.getWeakened() + playerStatuses.getFire();
        if (totalStatusCount == 0) {
            return;
        }
        int removalIndex = new Random().nextInt(totalStatusCount);

        removalIndex -= playerStatuses.getPoison();
        if (removalIndex <= 0) {
            playerStatuses.addPoison(-1);
            SwingRenderer.addHealthText("The " + getName() + " removed a level of poison!");
            SwingRenderer.UIUpdater(player);
            return;
        }

        removalIndex -= playerStatuses.getWeakened();
        if (removalIndex <= 0) {
            playerStatuses.addWeakened(-1);
            SwingRenderer.addHealthText("The " + getName() + " removed a level of weakness!");
            SwingRenderer.UIUpdater(player);
            return;
        }

        playerStatuses.addFire(-1);
        SwingRenderer.addHealthText("The " + getName() + " removed a level of fire!");
        SwingRenderer.UIUpdater(player);
    }
}
