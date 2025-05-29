package main.item;

import main.DialogueType;
import main.Main;
import main.Player;

import javax.swing.*;

public class DamageBuffItem extends BuffItem {
    public DamageBuffItem() {
        this.statType = "attack damage";
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        System.out.println(Main.colorString("You're not sure how, but your " + this.statType + " increased by " + this.amountChanged + ".", DialogueType.HEAL));
        player.increaseDamage(this.amountChanged);
    }
}
