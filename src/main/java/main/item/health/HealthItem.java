package main.item.health;

import main.entity.Player;
import main.item.Item;

import java.util.Random;

public class HealthItem extends Item {
    private final int restorationLowerBound;
    private final int restorationUpperBound;
    private final int addedAbsorption;

    public HealthItem(String name, String description, int value, double dropChance, int shopWeight, int restorationLowerBound, int restorationUpperBound, int addedAbsorption, boolean cleansable) {
        super(name, description, value, true, dropChance, shopWeight, cleansable);
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
            player.takeDamage(healthRestored * -1);
        } else {
            if (this.addedAbsorption != 0) {
                player.addAbsorption(this.addedAbsorption);
            }
            int amountHealed = player.heal(healthRestored);
            if (amountHealed != 0) {
            } else {
            }
        }
        player.discardItem(this);
    }
}
