package main.item.buff;

import main.Player;
import main.swing.SwingRenderer;

public class InventoryBuffItem extends BuffItem {
    public InventoryBuffItem() {
        this.setBounds(1, 3);
        this.statType = "inventory capacity";
    }
    @Override
    public void useItem(Player player) {
        super.useItem(player);
        SwingRenderer.addHealthText("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        player.changeInventoryCap(this.amountChanged);
    }
}
