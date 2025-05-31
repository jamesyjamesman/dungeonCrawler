package main.item;

import main.Player;
import main.SwingRenderer;

import javax.swing.*;

public class UnBuffItem extends BuffItem {
    public UnBuffItem() {
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        SwingRenderer.addHealthText(frame, "Blegh! You think that was meatballs. At some point.\nYou're really not feeling too good.\nYou took " + this.amountChanged + " damage!");
        //if I ever implement a poison mechanic it would be used here instead
        player.takeDamage(frame, this.amountChanged);
    }
}
