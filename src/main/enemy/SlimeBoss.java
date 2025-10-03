package main.enemy;

import main.Player;
import main.item.Item;
import main.item.Loot;
import main.item.buff.HealthBuffItem;
import main.item.relic.SlimeRelic;
import main.item.weapon.SlimeSpear;
import main.item.weapon.SlimeSword;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

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
        healthBuff.setDropChance(0.8);
        healthBuff.setBounds(4, 10);

        items.add(slimeSword);
        items.add(slimeSpear);
        items.add(healthBuff);
        items.add(new SlimeRelic());
        this.setLoot(new Loot(20, items));
    }

    public int slimeLaunchAttack() {
        int damage = this.getDamage() + 1;
        SwingRenderer.appendTextPane("The slime launches a slimeball at you, hitting you square in the face!\n", false, ComponentType.PANE_MAIN);
        return damage;
    }

    public int slimeChargeAttack() {
        int damage = this.getDamage() - 1;
        SwingRenderer.appendTextPane("The slime jumps at you, knocking you down!\n", false, ComponentType.PANE_MAIN);
        return damage;
    }

    public int slimeWait() {
        SwingRenderer.appendTextPane("The slime is taking a break.\n", false, ComponentType.PANE_MAIN);
        return 0;
    }

    //not good
    @Override
    public void attack(Player player) {
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
    public int takeDamage(int damage) {
        if (new Random().nextInt(5) == 0) {
            SwingRenderer.appendTextPane("Your attack bounced off of the slime's squishy exterior!\n", false, ComponentType.PANE_MAIN);
            return 0;
        }
        return super.takeDamage(damage);
    }

}
