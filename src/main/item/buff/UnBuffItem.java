package main.item.buff;

import main.Player;
import main.swing.SwingRenderer;

import java.util.Random;

public class UnBuffItem extends BuffItem {
    public UnBuffItem() {
        this(2, 7);
    }

    public UnBuffItem(int lowBound, int highBound) {
        super(lowBound, highBound, "poison");
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        int poisonAmount = new Random().nextInt(getLowBound(), getHighBound());
        //todo implement addpoison
        player.getCurrentStatuses().setPoison(player.getCurrentStatuses().getPoison() + poisonAmount);
        SwingRenderer.addHealthText("Blegh! You think that was meatballs. At some point.\nYou don't feel so good... You received " + poisonAmount + " levels of poison.");
        SwingRenderer.UIUpdater(player);
    }
}
