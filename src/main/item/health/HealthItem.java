package main.item.health;

import main.Player;
import main.item.Item;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.Random;

public class HealthItem extends Item {
    int healthRestored;
    int restorationLowerBound;
    int restorationUpperBound;
    int maxHealthChange;
    int addedAbsorption;
    public HealthItem() {
        this.healthRestored = 0;
        this.maxHealthChange = 0;
        this.restorationLowerBound = 0;
        this.restorationUpperBound = 0;
        this.addedAbsorption = 0;
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        this.healthRestored = new Random().nextInt(this.restorationLowerBound, this.restorationUpperBound);

        if (this.healthRestored < 0) {
            SwingRenderer.addHealthText(frame, "Yuck! The " + this.getName() + " was terrible... You lost " + this.healthRestored * -1 + " health.");
            player.takeDamage(frame, this.healthRestored * -1);
        } else {
            if (this.maxHealthChange != 0) {
                SwingRenderer.addHealthText(frame, "Wow! A surge of power courses through you... your maximum health has increased by " + this.maxHealthChange + "!");
                player.changeMaxHealth(this.maxHealthChange);
            }
            if (this.addedAbsorption != 0) {
                SwingRenderer.addHealthText(frame, "Wow! An aura of healthiness surrounds you... You gained " + this.addedAbsorption + " points of absorption!");
                player.addAbsorption(this.addedAbsorption);
            }
            int amountHealed = player.heal(this.healthRestored);
            if (amountHealed != 0) {
                SwingRenderer.addHealthText(frame, "Yum! That " + this.getName() + " was great! You replenished " + amountHealed + " health.");
            } else {
                SwingRenderer.addHealthText(frame, "That " + this.getName() + " was delicious! ...but you don't feel any healthier.");
            }
        }
        UIUpdater(frame, player);
        player.discardItem(frame, this);
    }

    public void setRestorationRange(int lowerBound, int upperBound) {
        this.restorationLowerBound = lowerBound;
        this.restorationUpperBound = upperBound;
    }
    public void setAddedAbsorption(int addedAbsorption) {
        this.addedAbsorption = addedAbsorption;
    }
}
