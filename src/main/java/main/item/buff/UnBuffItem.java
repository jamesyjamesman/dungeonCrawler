package main.item.buff;

import main.entity.Player;

public class UnBuffItem extends BuffItem {
    public UnBuffItem() {
        this(1, 2, 7);
    }
    public UnBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public UnBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "poison");
    }

    @Override
    public String useItem(Player player) {
        int poisonAmount = randomizeAmountChanged();
        player.getCurrentStatuses().addPoison(poisonAmount);
        player.discardItem(this);
        if (poisonAmount > 0)
            return "Gross! That... was not worth it. You gained " + poisonAmount + " levels of poison!";
        else
            return "That was disgusting!";

    }
}
