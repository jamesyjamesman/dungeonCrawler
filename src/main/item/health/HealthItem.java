package main.item.health;

import main.entity.Player;
import main.item.Item;
import main.swing.SwingRenderer;

import java.util.Random;

public class HealthItem extends Item {
    private final int restorationLowerBound;
    private final int restorationUpperBound;
    private final int addedAbsorption;

    public HealthItem(String name, String description, int value, double dropChance, int shopWeight, int restorationLowerBound, int restorationUpperBound, int addedAbsorption) {
        super(name, description, value, true, dropChance, shopWeight);
        this.restorationLowerBound = restorationLowerBound;
        this.restorationUpperBound = restorationUpperBound;
        this.addedAbsorption = addedAbsorption;
    }

    public HealthItem(String name, String description, int value, double dropChance, int shopWeight, int restorationLowerBound, int restorationUpperBound) {
        super(name, description, value, true, dropChance, shopWeight);
        this.restorationLowerBound = restorationLowerBound;
        this.restorationUpperBound = restorationUpperBound;
        this.addedAbsorption = 0;
    }

    @Override
    public void useItem(Player player) {
        int healthRestored = new Random().nextInt(this.restorationLowerBound, this.restorationUpperBound);

        if (healthRestored < 0) {
            SwingRenderer.addHealthText("Yuck! The " + this.getName() + " was terrible... You lost " + healthRestored * -1 + " health.");
            player.takeDamage(healthRestored * -1);
        } else {
            if (this.addedAbsorption != 0) {
                SwingRenderer.addHealthText("Wow! An aura of healthiness surrounds you... You gained " + this.addedAbsorption + " points of absorption!");
                player.addAbsorption(this.addedAbsorption);
            }
            int amountHealed = player.heal(healthRestored);
            if (amountHealed != 0) {
                SwingRenderer.addHealthText("Yum! That " + this.getName() + " was great! You replenished " + amountHealed + " health.");
            } else {
                SwingRenderer.addHealthText("That " + this.getName() + " was delicious! ...but you don't feel any healthier.");
            }
        }
        SwingRenderer.UIUpdater(player);
        player.discardItem(this);
    }
}
