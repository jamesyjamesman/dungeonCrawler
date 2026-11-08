package main.item.relic;

import main.App;
import main.Statuses;
import main.entity.Player;
import main.requests.ItemUseCase;

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
    public ItemUseCase useItem(Player player) {
        if (App.INSTANCE.getState() == App.State.ROOM_END || App.INSTANCE.getState() == App.State.PLAYER_TURN) {
            Statuses playerStatuses = player.getCurrentStatuses();
            int totalStatusCount = playerStatuses.getPoison() + playerStatuses.getWeakened() + playerStatuses.getFire();
            if (totalStatusCount > 0) {
                int removalIndex = new Random().nextInt(totalStatusCount);

                removalIndex -= playerStatuses.getPoison();
                if (removalIndex <= 0 && playerStatuses.getPoison() > 0)
                    playerStatuses.addPoison(-1);

                removalIndex -= playerStatuses.getWeakened();
                if (removalIndex <= 0 && playerStatuses.getPoison() > 0)
                    playerStatuses.addWeakened(-1);

                if (playerStatuses.getFire() > 0) {
                    playerStatuses.addFire(-1);
                    return ItemUseCase.RELIC_USED;
                } else return ItemUseCase.ILLEGAL_STATE;
            }
        }
        return ItemUseCase.NO_GENERIC_USE;
    }
}
