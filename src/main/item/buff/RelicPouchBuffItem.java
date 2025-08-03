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
        if (this.amountChanged > 0) {
            SwingRenderer.addHealthText("Forcing yourself to swallow the contents of the can, your " + this.statType + " somehow increased by " + this.amountChanged + ".");
            player.changeRelicCap(this.amountChanged);
        } else {
            SwingRenderer.addHealthText("Drinking that... was not worth it.");
        }
        SwingRenderer.UIUpdater(player);
    }
}
