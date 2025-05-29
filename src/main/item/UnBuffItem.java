package main.item;

import main.DialogueType;
import main.Main;
import main.Player;

import javax.swing.*;

public class UnBuffItem extends BuffItem {
    public UnBuffItem() {
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        super.useItem(frame, player);
        System.out.println("Blegh! You think that was meatballs. At some point.");
        System.out.println("You're really not feeling too good.");
        //if I ever implement a poison mechanic it would be used here instead
        System.out.println(Main.colorString("You took " + this.amountChanged + " damage!", DialogueType.DAMAGE));
        player.takeDamage(this.amountChanged);
    }
}
