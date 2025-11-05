package main.item.buff;

import main.entity.Player;

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
    public void useItem(Player player) {
        super.useItem(player);
        int amountChanged = randomizeAmountChanged();
        player.changeInventoryCap(amountChanged);
    }
}
