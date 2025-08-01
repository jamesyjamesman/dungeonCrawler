package main.enemy;

import main.Player;
import main.item.Item;
import main.item.Loot;
import main.item.buff.HealthBuffItem;
import main.item.relic.Relic;
import main.item.relic.RelicID;
import main.item.relic.SlimeRelic;
import main.item.weapon.SlimeSpear;
import main.item.weapon.SlimeSword;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class SlimeBoss extends Boss {
    public SlimeBoss() {
        this.setMaxHealth(30);
        this.setSpecies("slime");
        this.setDamage(3);
        this.setExperienceDropped(30);

        ArrayList<Item> items = new ArrayList<>();
        SlimeSword slimeSword = new SlimeSword();
        SlimeSpear slimeSpear = new SlimeSpear();
        HealthBuffItem healthBuff = new HealthBuffItem();

        slimeSword.setDropChance(0.7);
        slimeSpear.setDropChance(0.3);
        healthBuff.setBounds(4, 10);

        items.add(slimeSword);
        items.add(slimeSpear);
        items.add(healthBuff);
        this.setLoot(new Loot(20, items));
    }

    public int slimeLaunchAttack() {
        int damage = this.damage + 1;
        SwingRenderer.appendMainLabelText("The slime launches a slimeball at you, hitting you square in the face!\n", false);
        return damage;
    }

    public int slimeChargeAttack() {
        int damage = this.damage - 1;
        SwingRenderer.appendMainLabelText("The slime jumps at you, knocking you down!\n", false);
        return damage;
    }

    public int slimeWait() {
        SwingRenderer.appendMainLabelText("The slime is taking a break.\n", false);
        return 0;
    }

    //not good
    @Override
    public void attack(JFrame frame, Player player) {
        if (player.equippedRelicIndex(RelicID.SLIME) > -1
                && new Random().nextInt(4) == 0) {
            SwingRenderer.appendMainLabelText("The attack from the " + this.species + " bounced right off!", false);
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
            SwingRenderer.addHealthText("You took " + damage + " damage from the slime!");
        }
        player.takeDamage(damage);
    }

    @Override
    public int takeDamage(JFrame frame, int damage) {
        if (new Random().nextInt(5) == 0) {
            SwingRenderer.appendMainLabelText("Your attack bounced off of the slime's squishy exterior!\n", false);
            return 0;
        }
        return super.takeDamage(frame, damage);
    }

    @Override
    public Relic initializeBossRelic() {
        return new SlimeRelic();
    }
}
