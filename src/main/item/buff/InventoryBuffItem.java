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
        SwingRenderer.addHealthText("Forcing yourself to swallow the contents of the can, your " + this.statType + " somehow increased by " + this.amountChanged + ".");
        player.changeInventoryCap(this.amountChanged);
        SwingRenderer.UIUpdater(player);
    }
}
