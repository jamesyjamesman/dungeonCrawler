package Main.Item;

import Main.Player;

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
    public void useItem(Player player) {
        randomizeAmountChanged();
        System.out.println("You crack open the can, plug your nose, and manage to force its contents down your throat.");
        System.out.println("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
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
