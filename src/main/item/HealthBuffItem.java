package main.item;

import main.Player;

public class HealthBuffItem extends BuffItem {
    public HealthBuffItem() {
        this.statType = "maximum health";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        player.changeMaxHealth(this.amountChanged);
    }
}
