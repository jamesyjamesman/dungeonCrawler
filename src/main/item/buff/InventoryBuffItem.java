package main.item.buff;

import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;

public class InventoryBuffItem extends BuffItem {
    public InventoryBuffItem() {
        this.setBounds(1, 3);
        this.statType = "inventory capacity";
    }
    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        SwingRenderer.addHealthText(frame, "You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        player.changeInventoryCap(this.amountChanged);
    }
}
