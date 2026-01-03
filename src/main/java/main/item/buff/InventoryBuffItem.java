package main.item.buff;

import main.entity.Player;
import main.requests.ItemUseCase;

public class InventoryBuffItem extends BuffItem {
    public InventoryBuffItem() {
        this(1, 1, 3);
    }
    public InventoryBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public InventoryBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "inventory capacity");
    }

    @Override
    public ItemUseCase useItem(Player player) {
        player.changeInventoryCap(amountChanged);
        player.discardItem(this);
        return ItemUseCase.BUFF;
    }
}
