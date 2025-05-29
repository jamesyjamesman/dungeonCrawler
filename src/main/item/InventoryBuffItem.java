package main.item;

import main.DialogueType;
import main.Main;
import main.Player;

import javax.swing.*;

public class InventoryBuffItem extends BuffItem {
    public InventoryBuffItem() {
        this.statType = "inventory capacity";
    }
    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        System.out.println(Main.colorString("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".", DialogueType.HEAL));
        player.changeInventoryCap(this.amountChanged);
    }
}
