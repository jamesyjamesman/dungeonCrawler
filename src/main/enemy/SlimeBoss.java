package main.enemy;

import main.Main;
import main.Player;
import main.item.Relic;
import main.item.SlimeRelic;

import java.util.Random;

public class SlimeBoss extends Boss {
    public SlimeBoss() {

    }

    @Override
    public void bossAbilities() {

    }

    public int slimeLaunchAttack() {
        int damage = this.damage + 1;
        System.out.println("The slime launches a slimeball at you, hitting you square in the face!");
        return damage;
    }

    public int slimeChargeAttack() {
        int damage = this.damage - 1;
        System.out.println("The slime jumps at you, knocking you down!");
        return damage;
    }

    public int slimeWait() {
        System.out.println("The slime is taking a break.");
        return 0;
    }

    //not good
    @Override
    public void attack(Player player) {
        if (player.equippedRelicIndex("Relic of Bounciness") > -1
                && new Random().nextInt(4) == 0) {
            System.out.println("The attack from the " + this.species + " bounced right off!");
            return;
        }
        int damage;
        int randomChoice = new Random().nextInt(3);

        if (randomChoice == 0) {
            damage = slimeLaunchAttack();
        } else if (randomChoice == 1) {
            damage = slimeChargeAttack();
        } else {
            damage = slimeWait();
        }
        if (damage > 0) {
            System.out.println("You took " + damage + " damage!");
        }
        player.takeDamage(damage);
    }

    @Override
    public int takeDamage(int damage) {
        if (new Random().nextInt(5) == 0) {
            System.out.println("Your attack bounced off of the slime's squishy exterior!");
            return 0;
        }
        return super.takeDamage(damage);
    }

    @Override
    public Relic initializeBossRelic() {
        return new SlimeRelic();
    }

    @Override
    public void death(Player player) {
        Relic slimeRelic = initializeBossRelic();
        System.out.println("Wow! You defeated the slime boss! Lucky for you, it seems to have dropped something!");
        //this should be a function somewhere
        System.out.println("Would you like to add the " + slimeRelic.getName() + " to your inventory? (y/n)");
        String response = Main.yesOrNo();
        if (response.equals("y")) {
            player.addItemToInventory(slimeRelic);
            System.out.println("Would you like to equip it now?");
            response = Main.yesOrNo();
            if (response.equals("y")) {
                slimeRelic.useItem(player);
                System.out.println("The " + slimeRelic.getName() + " was equipped!");
            }
        } else {
            System.out.println("Really...?");
        }
    }
}
