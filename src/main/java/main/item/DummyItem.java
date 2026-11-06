package main.item;

import main.entity.Player;
import main.requests.ItemUseCase;

public class DummyItem extends Item {
    public DummyItem() {
        super("Dummy", "If you can see me, the game broke.", 0, true, 1, 0, ItemType.HEALTH);
    }
    @Override
    public ItemUseCase useItem(Player player) {
        return ItemUseCase.NO_GENERIC_USE;
    }
}
