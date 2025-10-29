package main.item.buff;

import main.entity.Player;
import main.swing.SwingRenderer;

public class HealthBuffItem extends BuffItem {
    public HealthBuffItem() {
        this(1, 1, 4);
    }
    public HealthBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public HealthBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "maximum health");
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        int amountChanged = randomizeAmountChanged();
        SwingRenderer.addHealthText("Forcing yourself to swallow the contents of the can, your " + this.statName + " somehow increased by " + amountChanged + ".");
        player.increaseMaxHealth(amountChanged);
        SwingRenderer.UIUpdater(player);
    }
}
