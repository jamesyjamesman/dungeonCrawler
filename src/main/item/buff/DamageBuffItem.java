package main.item.buff;

import main.entity.Player;
import main.swing.SwingRenderer;

public class DamageBuffItem extends BuffItem {
    public DamageBuffItem() {
        this(1, 3);
    }
    public DamageBuffItem(int lowBound, int highBound) {
        super(lowBound, highBound, "attack damage");
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        SwingRenderer.addHealthText("You force down the contents of the can, your " + this.statName + " somehow increased by " + this.amountChanged + ".");
        player.increaseDamage(this.amountChanged);
        SwingRenderer.UIUpdater(player);
    }
}
