package main.item.buff;

import main.Player;
import main.swing.SwingRenderer;

public class HealthBuffItem extends BuffItem {
    public HealthBuffItem() {
        this(1, 4);
    }
    public HealthBuffItem(int lowBound, int highBound) {
        super(lowBound, highBound, "maximum health");
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        SwingRenderer.addHealthText("Forcing yourself to swallow the contents of the can, your " + this.statName + " somehow increased by " + this.amountChanged + ".");
        player.increaseMaxHealth(this.amountChanged);
        SwingRenderer.UIUpdater(player);
    }
}
