package main.item.buff;

import main.entity.Player;
import main.swing.SwingRenderer;

public class InventoryBuffItem extends BuffItem {
    public InventoryBuffItem() {
        this(1, 1, 3);
    }
    public InventoryBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public InventoryBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "inventory capacity");
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        SwingRenderer.addHealthText("Forcing yourself to swallow the contents of the can, your " + this.statName + " somehow increased by " + this.amountChanged + ".");
        player.changeInventoryCap(this.amountChanged);
        SwingRenderer.UIUpdater(player);
    }
}
