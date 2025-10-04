package main.item.buff;

import main.Player;
import main.item.Item;

import java.util.Random;

public abstract class BuffItem extends Item {
    protected final String statName;
    protected int amountChanged;
    private final int lowBound;
    private final int highBound;
    public BuffItem(int lowBound, int highBound, String statName) {
        this.statName = statName;
        this.setValue(10);
        this.amountChanged = 0;
        this.lowBound = lowBound;
        this.highBound = highBound;
        setName("Suspicious Can");
        setDescription("A can of... something. It's chunky.");
        this.setShopWeight(4);
    }

    @Override
    public void useItem(Player player) {
        randomizeAmountChanged();
        player.discardItem(this);
    }

    //sets amount changed from the lower bound to the upper bound - 1
    //why did I do this like this? the amount can just be randomized inside the useItem method...
    public void randomizeAmountChanged() {
        this.amountChanged = new Random().nextInt(this.lowBound, this.highBound);
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
