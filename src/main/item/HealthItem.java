package main.item;

import main.Player;

import java.util.Random;

public class HealthItem extends Item{
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
    public void useItem(Player player) {
        this.healthRestored = new Random().nextInt(this.restorationLowerBound, this.restorationUpperBound);

        if (this.healthRestored < 0) {
            System.out.println("Yuck! The " + this.name + " was terrible... You lost " + this.healthRestored * -1 + " health.");
            player.takeDamage(this.healthRestored * -1);
        } else {
            if (this.maxHealthChange != 0) {
                System.out.println("Wow! A surge of power courses through you... your maximum health has increased by " + this.maxHealthChange + "!");
                player.changeMaxHealth(this.maxHealthChange);
            }
            if (this.addedAbsorption != 0) {
                System.out.println("Wow! An aura of healthiness surrounds you... You gained " + this.addedAbsorption + " points of absorption!");
                player.addAbsorption(this.addedAbsorption);
            }
            int amountHealed = player.heal(this.healthRestored);
            if (amountHealed != 0) {
                System.out.println("Yum! That " + this.name + " was great! You replenished " + amountHealed + " health.");
            } else {
                System.out.println("That " + this.name + " was delicious! ...but you don't feel any healthier.");
            }
        }
        player.discardItem(this);
    }

    public void setRestorationRange(int lowerBound, int upperBound) {
        this.restorationLowerBound = lowerBound;
        this.restorationUpperBound = upperBound;
    }
    public void setMaxHealthChange(int maxHealthChange) {
        this.maxHealthChange = maxHealthChange;
    }
    public void setAddedAbsorption(int addedAbsorption) {
        this.addedAbsorption = addedAbsorption;
    }
}
