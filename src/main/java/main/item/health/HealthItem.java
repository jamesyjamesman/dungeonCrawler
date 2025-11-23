package main.item.health;

import main.entity.Player;
import main.item.Item;
import main.item.ItemType;

import java.util.Random;

public class HealthItem extends Item {
    private final int restorationLowerBound;
    private final int restorationUpperBound;
    private final int addedAbsorption;

    public HealthItem(String name, String description, int value, double dropChance, int shopWeight, int restorationLowerBound, int restorationUpperBound, int addedAbsorption, boolean cleansable) {
        super(name, description, value, true, dropChance, shopWeight, ItemType.HEALTH, cleansable);
        this.restorationLowerBound = restorationLowerBound;
        this.restorationUpperBound = restorationUpperBound;
        this.addedAbsorption = addedAbsorption;
    }

    public HealthItem(String name, String description, int value, double dropChance, int shopWeight, int restorationLowerBound, int restorationUpperBound) {
        super(name, description, value, true, dropChance, shopWeight, ItemType.HEALTH);
        this.restorationLowerBound = restorationLowerBound;
        this.restorationUpperBound = restorationUpperBound;
        this.addedAbsorption = 0;
    }

    @Override
    public String useItem(Player player) {
        int healthRestored = new Random().nextInt(this.restorationLowerBound, this.restorationUpperBound);
        String output;

        if (healthRestored < 0) {
            player.takeDamage(healthRestored * -1);
            output =  "Yuck! That " + this.getName() + " was gross...";

        } else {

            int amountHealed = player.heal(healthRestored);
            if (amountHealed == 0) {
                if (this.addedAbsorption == 0)
                    output = "You ate the " + this.getName() + ", but nothing happened.";
                else
                    output = "The " + this.getName() + " granted " + this.addedAbsorption + " absorption, but no health!";
            }
            else {
                if (this.addedAbsorption != 0) {
                    player.addAbsorption(this.addedAbsorption);
                    output = "The " + this.getName() + " healed you for " + amountHealed + " health and gave " + this.addedAbsorption + " absorption!";
                } else
                    output = "The " + this.getName() + " healed you for " + amountHealed + " health!";
            }
        }
        player.discardItem(this);
        return output;
    }
}
