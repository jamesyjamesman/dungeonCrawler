package main.item;

import main.DialogueType;
import main.Main;
import main.Player;
import main.SwingRenderer;

import javax.swing.*;

public class InventoryBuffItem extends BuffItem {
    public InventoryBuffItem() {
        this.statType = "inventory capacity";
    }
    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        SwingRenderer.appendMainLabelText(frame, "You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        player.changeInventoryCap(this.amountChanged);
    }
}
