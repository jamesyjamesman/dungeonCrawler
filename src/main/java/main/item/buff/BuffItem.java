package main.item.buff;

import main.entity.Player;
import main.item.Item;
import main.item.ItemType;
import main.requests.ItemUseCase;

import java.util.Random;

public abstract class BuffItem extends Item {
    protected final String statName;
    protected int amountChanged = 0;
    protected final int highBound;
    protected final int lowBound;
    public BuffItem(double dropChance, int lowBound, int highBound, String statName) {
        super("Suspicious Can",
                "A can of... something. It's chunky.",
                10,
                true,
                dropChance,
                4,
                ItemType.BUFF);
        this.statName = statName;
        this.highBound = highBound;
        this.lowBound = lowBound;
    }

    @Override
    public ItemUseCase useItem(Player player) {
        this.amountChanged = new Random().nextInt(lowBound, highBound);
        player.discardItem(this);
        return ItemUseCase.BUFF;
    }

    public String createOutputString() {
        if (this.amountChanged > 0)
            return "You force the contents of the can down your throat... Somehow, you gained " + this.amountChanged + " " + this.statName + "!";
        else
            return "Drinking that... wasn't worth it.";
    }

    public String getStatName() {
        return this.statName;
    }
}
