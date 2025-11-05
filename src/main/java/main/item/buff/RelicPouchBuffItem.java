package main.item.buff;

import main.entity.Player;

public class RelicPouchBuffItem extends BuffItem {
    public RelicPouchBuffItem() {
        this(1, 0, 2);
    }
    public RelicPouchBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public RelicPouchBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "relic pouch capacity");
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        int amountChanged = randomizeAmountChanged();
        if (amountChanged > 0) {
            player.changeRelicCap(amountChanged);
        } else {
        }
    }
}
