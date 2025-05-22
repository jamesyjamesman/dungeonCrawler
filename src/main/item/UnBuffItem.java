package main.item;

import main.Player;

public class UnBuffItem extends BuffItem {
    public UnBuffItem() {
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        System.out.println("Blegh! You think that was meatballs. At some point.");
        System.out.println("You're really not feeling too good.");
        //if I ever implement a poison mechanic it would be used here instead
        System.out.println("You took " + this.amountChanged + " damage!");
        player.takeDamage(this.amountChanged);
    }
}
