package main.item.relic;

import main.App;
import main.entity.Player;
import main.requests.ItemUseCase;

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
    public ItemUseCase useItem(Player player) {
        if (App.INSTANCE.getState() == App.State.ROOM_END) {
            if (new Random().nextInt(3) == 0) {
                int amountHealed = player.heal(new Random().nextInt(1, 5));
                if (amountHealed != 0) {
                    return ItemUseCase.RELIC_USED;
                }
            }
        }
        return ItemUseCase.NO_GENERIC_USE;
    }
}