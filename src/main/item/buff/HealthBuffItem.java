package main.item.buff;

import main.Player;
import main.swing.SwingRenderer;

public class HealthBuffItem extends BuffItem {
    public HealthBuffItem() {
        this.setBounds(1, 4);
        this.statType = "maximum health";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        SwingRenderer.addHealthText("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        player.changeMaxHealth(this.amountChanged);
    }
}
