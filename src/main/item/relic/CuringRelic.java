package main.item.relic;

import main.Statuses;
import main.entity.Player;
import main.room.Room;
import main.swing.SwingRenderer;

import java.util.Random;

public class CuringRelic extends Relic {
    public CuringRelic(String name, String description, double dropChance, RelicID relicID) {
        super(name, description, dropChance, relicID);
    }

    @Override
    public void useRelic(Player player, Room room) {
        Statuses playerStatuses = player.getCurrentStatuses();
        int totalStatusCount = playerStatuses.getPoison() + playerStatuses.getWeakened() + playerStatuses.getFire();
        int removalIndex = new Random().nextInt(totalStatusCount);

        removalIndex -= playerStatuses.getPoison();
        if (removalIndex <= 0) {
            playerStatuses.addPoison(-1);
            SwingRenderer.addHealthText("The " + getName() + " removed a level of poison!");
            return;
        }

        removalIndex -= playerStatuses.getWeakened();
        if (removalIndex <= 0) {
            playerStatuses.addWeakened(-1);
            SwingRenderer.addHealthText("The " + getName() + " removed a level of weakness!");
            return;
        }

        playerStatuses.addFire(-1);
        SwingRenderer.addHealthText("The " + getName() + " removed a level of fire!");
    }
}
