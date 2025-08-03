package main.item.buff;

import main.Player;
import main.item.Item;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.Random;

public abstract class BuffItem extends Item {
    String statType;
    int amountChanged;
    int lowBound;
    int highBound;
    public BuffItem() {
        this.statType = "";
        this.setValue(10);
        this.amountChanged = 0;
        this.lowBound = 0;
        this.highBound = 0;
        setName("Suspicious Can");
        setDescription("A can of... something. It's chunky.");
        this.setShopWeight(5);
    }

    @Override
    public void useItem(Player player) {
        randomizeAmountChanged();
        SwingRenderer.appendTextPane("You crack open the can, plug your nose, and manage to force its contents down your throat.", false, ComponentType.PANE_MAIN);
        player.discardItem(this);
        SwingRenderer.UIUpdater(player);
    }

    //sets amount changed from the lower bound to the upper bound - 1
    //why did I do this like this? the amount can just be randomized inside the useItem method...
    public void randomizeAmountChanged() {
        this.amountChanged = new Random().nextInt(this.lowBound, this.highBound);
    }

    public void setBounds(int lowBound, int highBound) {
        this.lowBound = lowBound;
        this.highBound = highBound;
    }
}
