package main.item;

import main.Player;

public class RelicPouchBuffItem extends BuffItem {
    public RelicPouchBuffItem() {
        this.statType = "relic pouch capacity";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        if (amountChanged > 0) {
            System.out.println("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".");
        } else {
            System.out.println("Ew. That one was a dud.");
        }
        player.changeRelicCap(this.amountChanged);
    }
}
