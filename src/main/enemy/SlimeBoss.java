package main.enemy;

import main.DialogueType;
import main.Main;
import main.Player;
import main.item.relic.Relic;
import main.item.relic.SlimeRelic;

import javax.swing.*;
import java.util.Random;

public class SlimeBoss extends Boss {
    public SlimeBoss() {
    }

    public int slimeLaunchAttack() {
        int damage = this.damage + 1;
        System.out.println(Main.colorString("The slime launches a slimeball at you, hitting you square in the face!", DialogueType.BATTLE));
        return damage;
    }

    public int slimeChargeAttack() {
        int damage = this.damage - 1;
        System.out.println(Main.colorString("The slime jumps at you, knocking you down!", DialogueType.BATTLE));
        return damage;
    }

    public int slimeWait() {
        System.out.println(Main.colorString("The slime is taking a break.", DialogueType.BATTLE));
        return 0;
    }

    //not good
    @Override
    public void attack(JFrame frame, Player player) {
        if (player.equippedRelicIndex("Relic of Bounciness") > -1
                && new Random().nextInt(4) == 0) {
            System.out.println(Main.colorString("The attack from the " + this.species + " bounced right off!", DialogueType.BATTLE));
            return;
        }
        int damage;
        int randomChoice = new Random().nextInt(5);

        if (randomChoice < 2) { //40%
            damage = slimeLaunchAttack();
        } else if (randomChoice < 4) { //40%
            damage = slimeChargeAttack();
        } else { //20%
            damage = slimeWait();
        }
        if (damage > 0) {
            System.out.println(Main.colorString("You took " + damage + " damage!", DialogueType.DAMAGE));
        }
        player.takeDamage(frame, damage);
    }

    @Override
    public int takeDamage(int damage) {
        if (new Random().nextInt(5) == 0) {
            System.out.println(Main.colorString("Your attack bounced off of the slime's squishy exterior!", DialogueType.BATTLE));
            return 0;
        }
        return super.takeDamage(damage);
    }

    @Override
    public Relic initializeBossRelic() {
        return new SlimeRelic();
    }
}
