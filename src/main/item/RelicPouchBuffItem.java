package main.item;

import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;

public class RelicPouchBuffItem extends BuffItem {
    public RelicPouchBuffItem() {
        this.statType = "relic pouch capacity";
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        if (amountChanged > 0) {
            SwingRenderer.addHealthText(frame, "You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        } else {
            SwingRenderer.addHealthText(frame, "Ew. That one was a dud.");
        }
        player.changeRelicCap(this.amountChanged);
    }
}
