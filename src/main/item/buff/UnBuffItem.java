package main.item.buff;

import main.entity.Player;
import main.swing.SwingRenderer;

import java.util.Random;

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
    public void useItem(Player player) {
        super.useItem(player);
        int poisonAmount = new Random().nextInt(getLowBound(), getHighBound());
        player.getCurrentStatuses().addPoison(poisonAmount);
        SwingRenderer.addHealthText("Blegh! You think that was meatballs. At some point.\nYou don't feel so good... You received " + poisonAmount + " levels of poison.");
        SwingRenderer.UIUpdater(player);
    }
}
