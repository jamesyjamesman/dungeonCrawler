package main.item;

import main.Player;

public class DamageBuffItem extends BuffItem {
    public DamageBuffItem() {
        this.statType = "attack damage";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        player.increaseDamage(this.amountChanged);
    }
}
