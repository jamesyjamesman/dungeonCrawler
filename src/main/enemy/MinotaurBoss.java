package main.enemy;

import main.Player;
import main.item.Loot;
import main.swing.SwingRenderer;
import main.item.relic.CurseHealRelic;
import main.item.relic.Relic;

import javax.swing.*;
import java.util.Random;

public class MinotaurBoss extends Boss {
    public MinotaurBoss() {
        this.setLoot(new Loot(30));
    }
    public int chargeAttack(JFrame frame) {
        SwingRenderer.appendMainLabelText(frame, "The minotaur charges at you, goring you with its horns!\n", false);
        return this.damage * 2;
    }
    public int shriekAttack(JFrame frame, Player player) {
        player.getCurrentStatuses().setWeakened(player.getCurrentStatuses().getWeakened() + 1);
        SwingRenderer.appendMainLabelText(frame, "The minotaur unleashes a piercing scream, wracking your nerves! (You gained a level of weakness).\n", false);
        return this.damage;
    }
    public int angerAttack(JFrame frame) {
        SwingRenderer.appendMainLabelText(frame, "The minotaur is getting upset!\n", false);
        this.damage += 1;
        return 0;
    }
    public int healAttack(JFrame frame) {
        SwingRenderer.appendMainLabelText(frame, "The minotaur pulls out a large chunk of meat, ripping into it!\n", false);
        this.currentHealth += (int) (this.damage * 1.5);
        return 0;
    }
    public int failedChargeAttack(JFrame frame) {
        SwingRenderer.appendMainLabelText(frame, "The minotaur tries to charge at you, but falls over, smacking itself in the face.\n", false);
        this.currentHealth -= this.damage / 2;
        return angerAttack(frame);
    }

    //minotaur is not affected by relic of bounciness (on purpose)
    @Override
    public void attack(JFrame frame, Player player) {
        int damage = 0;
        int randomChoice = new Random().nextInt(9);

        switch (randomChoice) {
            case 0 -> damage = failedChargeAttack(frame);
            case 1, 2 -> damage = angerAttack(frame);
            case 3, 4, 5 -> damage = shriekAttack(frame, player);
            case 6, 7 -> damage = chargeAttack(frame);
            case 8 -> damage = healAttack(frame);
        }

        if (damage > 0) {
            SwingRenderer.addHealthText(frame, "You took " + damage + " damage from the minotaur!");
        }
        player.takeDamage(frame, damage);
    }

    @Override
    public Relic initializeBossRelic() {
        return new CurseHealRelic();
    }
}
