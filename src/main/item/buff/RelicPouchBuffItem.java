package main.item.buff;

import main.Player;
import main.swing.SwingRenderer;

public class RelicPouchBuffItem extends BuffItem {
    public RelicPouchBuffItem() {
        this.setBounds(0, 2);
        this.statType = "relic pouch capacity";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        if (amountChanged > 0) {
            SwingRenderer.addHealthText("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        } else {
            SwingRenderer.addHealthText("Ew. That one was a dud.");
        }
        player.changeRelicCap(this.amountChanged);
    }
}
