package main.item;

import main.DialogueType;
import main.Main;
import main.Player;

import javax.swing.*;

public class HealthBuffItem extends BuffItem {
    public HealthBuffItem() {
        this.statType = "maximum health";
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        System.out.println(Main.colorString("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".", DialogueType.HEAL));
        player.changeMaxHealth(this.amountChanged);
    }
}
