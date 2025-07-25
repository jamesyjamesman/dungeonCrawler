package main.enemy;

import main.Player;
import main.item.ItemBlueprint;
import main.item.ItemID;
import main.item.Loot;
import main.item.relic.RelicID;
import main.swing.SwingRenderer;
import main.item.relic.Relic;
import main.item.relic.SlimeRelic;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class SlimeBoss extends Boss {
    public SlimeBoss() {
        ArrayList<ItemBlueprint> items = new ArrayList<>();
        items.add(new ItemBlueprint(0.7, ItemID.WEAPON_SWORD_SLIME));
        items.add(new ItemBlueprint(0.3, ItemID.WEAPON_SPEAR_SLIME));
        items.add(new ItemBlueprint(1.0, ItemID.BUFF_HEALTH));
        this.setLoot(new Loot(20, items));
    }

    public int slimeLaunchAttack(JFrame frame) {
        int damage = this.damage + 1;
        SwingRenderer.appendMainLabelText(frame, "The slime launches a slimeball at you, hitting you square in the face!\n", false);
        return damage;
    }

    public int slimeChargeAttack(JFrame frame) {
        int damage = this.damage - 1;
        SwingRenderer.appendMainLabelText(frame, "The slime jumps at you, knocking you down!\n", false);
        return damage;
    }

    public int slimeWait(JFrame frame) {
        SwingRenderer.appendMainLabelText(frame, "The slime is taking a break.\n", false);
        return 0;
    }

    //not good
    @Override
    public void attack(JFrame frame, Player player) {
        if (player.equippedRelicIndex(RelicID.SLIME) > -1
                && new Random().nextInt(4) == 0) {
            SwingRenderer.appendMainLabelText(frame, "The attack from the " + this.species + " bounced right off!", false);
            return;
        }
        int damage;
        int randomChoice = new Random().nextInt(5);

        if (randomChoice < 2) { //40%
            damage = slimeLaunchAttack(frame);
        } else if (randomChoice < 4) { //40%
            damage = slimeChargeAttack(frame);
        } else { //20%
            damage = slimeWait(frame);
        }
        if (damage > 0) {
            SwingRenderer.addHealthText(frame, "You took " + damage + " damage from the slime!");
        }
        player.takeDamage(frame, damage);
    }

    @Override
    public int takeDamage(JFrame frame, int damage) {
        if (new Random().nextInt(5) == 0) {
            SwingRenderer.appendMainLabelText(frame, "Your attack bounced off of the slime's squishy exterior!\n", false);
            return 0;
        }
        return super.takeDamage(frame, damage);
    }

    @Override
    public Relic initializeBossRelic() {
        return new SlimeRelic();
    }
}
