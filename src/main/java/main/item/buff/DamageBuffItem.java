package main.item.buff;

import main.entity.Player;

public class DamageBuffItem extends BuffItem {
    public DamageBuffItem() {
        this(1, 1, 3);
    }
    public DamageBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public DamageBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "attack damage");
    }

    @Override
    public String useItem(Player player) {
        int amountChanged = randomizeAmountChanged();
        player.increaseDamage(amountChanged);
        player.discardItem(this);
        return createOutputString(amountChanged);
    }
}
