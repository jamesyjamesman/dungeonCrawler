package main.item;

import main.DialogueType;
import main.Main;
import main.Player;

import javax.swing.*;

public class RelicPouchBuffItem extends BuffItem {
    public RelicPouchBuffItem() {
        this.statType = "relic pouch capacity";
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        if (amountChanged > 0) {
            System.out.println(Main.colorString("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".", DialogueType.HEAL));
        } else {
            System.out.println("Ew. That one was a dud.");
        }
        player.changeRelicCap(this.amountChanged);
    }
}
