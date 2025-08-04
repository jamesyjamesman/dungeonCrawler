package main.enemy;

import main.App;
import main.Player;
import main.item.Loot;
import main.item.relic.CurseHealRelic;
import main.room.EnemyRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.Random;

public class MinotaurBoss extends Boss {
    public MinotaurBoss() {
        this.setMaxHealth(50);
        this.setSpecies("minotaur");
        this.setDamage(5);
        this.setExperienceDropped(100);

        this.setLoot(new Loot(30, new CurseHealRelic()));
    }
    public int chargeAttack() {
        SwingRenderer.appendTextPane("The minotaur charges at you, goring you with its horns!\n", false, ComponentType.PANE_MAIN);
        return this.damage * 2;
    }
    public int shriekAttack(Player player) {
        player.getCurrentStatuses().setWeakened(player.getCurrentStatuses().getWeakened() + 1);
        SwingRenderer.appendTextPane("The minotaur unleashes a piercing scream, wracking your nerves! (You gained a level of weakness).\n", false, ComponentType.PANE_MAIN);
        return this.damage;
    }
    public int angerAttack() {
        SwingRenderer.appendTextPane("The minotaur is getting upset!\n", false, ComponentType.PANE_MAIN);
        this.damage += 1;
        return 0;
    }
    public int healAttack() {
        SwingRenderer.appendTextPane("The minotaur pulls out a large chunk of meat, ripping into it!\n", false, ComponentType.PANE_MAIN);
        this.currentHealth += (int) (this.damage * 1.5);
        return 0;
    }
    public int failedChargeAttack() {
        SwingRenderer.appendTextPane("The minotaur tries to charge at you, but falls over, smacking itself in the face.\n", false, ComponentType.PANE_MAIN);
        takeDamage(this.damage / 2);
        //thi
        if (this.getCurrentHealth() <= 0) {
            death(App.INSTANCE.getPlayer(), (EnemyRoom) App.INSTANCE.getPlayer().getCurrentRoom());
        }
        return angerAttack();
    }

    //minotaur is not affected by relic of bounciness (on purpose)
    @Override
    public void attack(Player player) {
        int damage = 0;
        int randomChoice = new Random().nextInt(9);

        switch (randomChoice) {
            case 0 -> damage = failedChargeAttack();
            case 1, 2 -> damage = angerAttack();
            case 3, 4, 5 -> damage = shriekAttack(player);
            case 6, 7 -> damage = chargeAttack();
            case 8 -> damage = healAttack();
        }

        if (damage > 0) {
            SwingRenderer.addHealthText("You took " + damage + " damage from the minotaur!");
        }
        player.takeDamage(damage);
    }

}
