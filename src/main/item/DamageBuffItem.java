package main.item;

import main.Player;

public class DamageBuffItem extends BuffItem {
    public DamageBuffItem() {
        this.statType = "attack damage";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        System.out.println("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        player.increaseDamage(this.amountChanged);
    }
}
