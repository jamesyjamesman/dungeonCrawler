package main.item.buff;

import main.entity.Player;

public class HealthBuffItem extends BuffItem {
    public HealthBuffItem() {
        this(1, 1, 4);
    }
    public HealthBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public HealthBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "maximum health");
    }

    @Override
    public String useItem(Player player) {
        int amountChanged = randomizeAmountChanged();
        player.increaseMaxHealth(amountChanged);
        player.discardItem(this);
        return createOutputString(amountChanged);
    }
}
