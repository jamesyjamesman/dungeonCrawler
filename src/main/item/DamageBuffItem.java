package main.item;

import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;

public class DamageBuffItem extends BuffItem {
    public DamageBuffItem() {
        this.statType = "attack damage";
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        SwingRenderer.addHealthText(frame, "You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        player.increaseDamage(this.amountChanged);
    }
}
