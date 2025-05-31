package main.item;

import main.Player;
import main.SwingRenderer;

import javax.swing.*;

public class HealthBuffItem extends BuffItem {
    public HealthBuffItem() {
        this.statType = "maximum health";
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        SwingRenderer.addHealthText(frame, "You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        player.changeMaxHealth(this.amountChanged);
    }
}
