package main.item.buff;

import main.Player;
import main.swing.SwingRenderer;

public class DamageBuffItem extends BuffItem {
    public DamageBuffItem() {
        this.setBounds(1, 3);
        this.statType = "attack damage";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        SwingRenderer.addHealthText("Forcing yourself to swallow the contents of the can, your " + this.statType + " somehow increased by " + this.amountChanged + ".");
        player.increaseDamage(this.amountChanged);
        SwingRenderer.UIUpdater(player);
    }
}
