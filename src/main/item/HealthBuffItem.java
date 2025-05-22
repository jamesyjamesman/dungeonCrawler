package main.item;

import main.Player;

public class HealthBuffItem extends BuffItem {
    public HealthBuffItem() {
        this.statType = "maximum health";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        System.out.println("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        player.changeMaxHealth(this.amountChanged);
    }
}
