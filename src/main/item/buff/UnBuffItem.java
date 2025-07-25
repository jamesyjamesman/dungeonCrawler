package main.item.buff;

import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.Random;

public class UnBuffItem extends BuffItem {
    public UnBuffItem() {
        this.setBounds(2, 7);
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        int poisonAmount = new Random().nextInt(this.lowBound, this.highBound);
        player.getCurrentStatuses().setPoison(player.getCurrentStatuses().getPoison() + poisonAmount);
        SwingRenderer.addHealthText(frame, "Blegh! You think that was meatballs. At some point.\nYou don't feel so good... You received " + poisonAmount + " levels of poison.");
        UIUpdater(frame, player);
    }
}
