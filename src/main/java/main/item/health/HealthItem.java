package main.item.health;

import main.entity.Player;
import main.item.Item;
import main.item.ItemType;
import main.requests.ItemUseCase;

import java.util.Random;

public class HealthItem extends Item {
    private final int addedAbsorption;
    private final int lower;
    private final int higher;
    // Store on the object because useItem doesn't return it
    private int healthRestored = 0;

    public HealthItem(String name, String description, int value, double dropChance, int shopWeight, int restorationLowerBound, int restorationUpperBound, int addedAbsorption, boolean cleansable) {
        super(name, description, value, true, dropChance, shopWeight, ItemType.HEALTH, cleansable);
        this.lower = restorationLowerBound;
        this.higher = restorationUpperBound;
        this.addedAbsorption = addedAbsorption;
    }

    public HealthItem(String name, String description, int value, double dropChance, int shopWeight, int restorationLowerBound, int restorationUpperBound) {
        super(name, description, value, true, dropChance, shopWeight, ItemType.HEALTH);
        this.lower = restorationLowerBound;
        this.higher = restorationUpperBound;
        this.addedAbsorption = 0;
    }

    @Override
    public ItemUseCase useItem(Player player) {
        player.discardItem(this);

        int healAmount = new Random().nextInt(lower, higher);

        if (this.addedAbsorption != 0)
            player.addAbsorption(this.addedAbsorption);

        if (healAmount < 0) {
            this.healthRestored = player.takeDamage(healAmount * -1);
            return ItemUseCase.NEGATIVE_HEALTH;
        }

        this.healthRestored = player.heal(healAmount);
        if (this.healthRestored == 0)
            return ItemUseCase.NO_HEALTH;
        else
            return ItemUseCase.HEALTH;
    }

    public int getHealthRestored() {
        return this.healthRestored;
    }
    public int getAddedAbsorption() {
        return this.addedAbsorption;
    }
}
