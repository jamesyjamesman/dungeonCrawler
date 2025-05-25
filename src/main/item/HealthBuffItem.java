package main.item;

import main.DialogueType;
import main.Main;
import main.Player;

public class HealthBuffItem extends BuffItem {
    public HealthBuffItem() {
        this.statType = "maximum health";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        System.out.println(Main.colorString("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".", DialogueType.HEAL));
        player.changeMaxHealth(this.amountChanged);
    }
}
