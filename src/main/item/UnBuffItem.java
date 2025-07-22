package main.item;

import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.Random;

public class UnBuffItem extends BuffItem {
    public UnBuffItem() {
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        //todo: no more direct damage (once statuses are implemented)
        SwingRenderer.addHealthText(frame, "Blegh! You think that was meatballs. At some point.\nYou're really not feeling too good.\nYou took " + this.amountChanged + " damage!");
        player.takeDamage(frame, this.amountChanged);
        int poisonAmount = new Random().nextInt(3, 6);
        player.getCurrentStatuses().setPoison(player.getCurrentStatuses().getPoison() + poisonAmount);
        SwingRenderer.addHealthText(frame, "You don't feel so good... You received " + poisonAmount + " levels of poison.");
    }
}
