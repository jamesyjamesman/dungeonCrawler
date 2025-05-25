package main.enemy;

import main.DialogueType;
import main.Main;
import main.Player;
import main.item.relic.CurseHealRelic;
import main.item.relic.Relic;

import java.util.Random;

public class MinotaurBoss extends Boss {
    public MinotaurBoss() {
    }
    public int chargeAttack() {
        System.out.println(Main.colorString("The minotaur charges at you, goring you with its horns!", DialogueType.BATTLE));
        return this.damage * 2;
    }
    public int shriekAttack() {
        System.out.println(Main.colorString("The minotaur unleashes a piercing scream!", DialogueType.BATTLE));
        return this.damage;
    }
    public int angerAttack() {
        System.out.println(Main.colorString("The minotaur is getting upset!", DialogueType.BATTLE));
        this.damage += 1;
        return 0;
    }
    public int healAttack() {
        System.out.println(Main.colorString("The minotaur pulls out a large chunk of meat, ripping into it!", DialogueType.BATTLE));
        this.currentHealth += this.damage * 2;
        return 0;
    }
    public int failedChargeAttack() {
        System.out.println(Main.colorString("The minotaur tries to charge at you, but falls over, smacking itself in the face.", DialogueType.BATTLE));
        this.currentHealth -= this.damage / 2;
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
            case 3, 4, 5 -> damage = shriekAttack();
            case 6, 7 -> damage = chargeAttack();
            case 8 -> damage = healAttack();
        }

        if (damage > 0) {
            System.out.println(Main.colorString("You took " + damage + " damage!", DialogueType.DAMAGE));
        }
        player.takeDamage(damage);
    }

    @Override
    public Relic initializeBossRelic() {
        return new CurseHealRelic();
    }
}
