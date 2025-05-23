package main.item.relic;

import main.Player;
import main.room.Room;

import java.util.Random;

public class ShieldingRelic extends Relic {
    public ShieldingRelic() {
        setName("Relic of Shielding");
        setDescription("A shimmering relic that gives a constant source of shielding.");
    }

    @Override
    public void useRelic(Player player, Room room) {
        int absorptionAmount = player.heal(new Random().nextInt(1,3));
        System.out.println("Your Relic of Shielding gave you " + absorptionAmount + " points of absorption!");
        super.useRelic(player, room);
    }
}
