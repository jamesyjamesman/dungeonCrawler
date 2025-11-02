package main.item.buff;

import main.entity.Player;
import main.item.Item;

import java.util.Random;

public abstract class BuffItem extends Item {
    protected final String statName;
    private final int lowBound;
    private final int highBound;
    public BuffItem(double dropChance, int lowBound, int highBound, String statName) {
        super("Suspicious Can",
                "A can of... something. It's chunky.",
                10,
                true,
                dropChance,
                4);
        this.statName = statName;
        this.lowBound = lowBound;
        this.highBound = highBound;
    }

    @Override
    public void useItem(Player player) {
        player.discardItem(this);
    }

    //sets amount changed from the lower bound to the upper bound - 1
    public int randomizeAmountChanged() {
        return new Random().nextInt(this.lowBound, this.highBound);
    }

    public int getLowBound() {
        return this.lowBound;
    }

    public int getHighBound() {
        return this.highBound;
    }

    public String getStatName() {
        return this.statName;
    }
}
