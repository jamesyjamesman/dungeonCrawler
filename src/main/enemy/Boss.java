package main.enemy;

import main.Main;
import main.Player;
import main.item.relic.Relic;

public abstract class Boss extends Enemy {
    public Boss() {
    }

    public abstract Relic initializeBossRelic();

    public void death(Player player) {
        Relic droppedRelic = initializeBossRelic();
        System.out.println("Wow! You defeated the " + this.species + " boss! Lucky for you, it seems to have dropped something!");
        System.out.println("Would you like to take the " + droppedRelic.getName() + "? (y/n)");
        String response = Main.yesOrNo();
        if (response.equals("y")) {
            player.itemPickup(droppedRelic);
        } else {
            System.out.println("Really...?");
        }
    }
}
