package main.item;

import main.Player;

public class InventoryBuffItem extends BuffItem {
    public InventoryBuffItem() {
        this.statType = "inventory capacity";
    }
    @Override
    public void useItem(Player player) {
        super.useItem(player);
        System.out.println("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        player.changeInventoryCap(this.amountChanged);
    }
}
