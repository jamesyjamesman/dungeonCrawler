package main.item.buff;

import main.entity.Player;
import main.swing.SwingRenderer;

public class RelicPouchBuffItem extends BuffItem {
    public RelicPouchBuffItem() {
        this(1, 0, 2);
    }
    public RelicPouchBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public RelicPouchBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "relic pouch capacity");
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        int amountChanged = randomizeAmountChanged();
        if (amountChanged > 0) {
            SwingRenderer.addHealthText("Forcing yourself to swallow the contents of the can, your " + this.statName + " somehow increased by " + amountChanged + ".");
            player.changeRelicCap(amountChanged);
        } else {
            SwingRenderer.addHealthText("Drinking that... was not worth it.");
        }
        SwingRenderer.UIUpdater(player);
    }
}
