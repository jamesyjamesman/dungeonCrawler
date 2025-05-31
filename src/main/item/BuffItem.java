package main.item;

import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.Random;

public abstract class BuffItem extends Item {
    String statType;
    int amountChanged;
    int lowBound;
    int highBound;
    public BuffItem() {
        this.statType = "";
        this.amountChanged = 0;
        this.lowBound = 0;
        this.highBound = 0;
        this.name = "Suspicious Can";
        this.description = "A can of... something. It's chunky.";
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        randomizeAmountChanged();
        SwingRenderer.appendMainLabelText(frame, "You crack open the can, plug your nose, and manage to force its contents down your throat.");
        player.discardItem(this);
    }

    //sets amount changed from the lower bound to the upper bound - 1
    public void randomizeAmountChanged() {
        this.amountChanged = new Random().nextInt(this.lowBound, this.highBound);
    }

    public void setBounds(int lowBound, int highBound) {
        this.lowBound = lowBound;
        this.highBound = highBound;
    }
}
