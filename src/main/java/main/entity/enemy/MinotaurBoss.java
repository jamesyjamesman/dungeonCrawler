package main.entity.enemy;

import main.entity.Player;
import main.entity.Species;
import main.item.Loot;
import main.item.relic.CurseHealRelic;

import java.util.Random;

public class MinotaurBoss extends Boss {
    public MinotaurBoss() {
        super(Species.MINOTAUR, 50, 5, 100);

        this.setLoot(new Loot(30, new CurseHealRelic()));
    }
    public int chargeAttack() {
        return this.getDamage() * 2;
    }
    public int shriekAttack(Player player) {
        player.getCurrentStatuses().addWeakened(1);
        return this.getDamage();
    }
    public int angerAttack() {
        this.setDamage(this.getDamage() + 1);
        return 0;
    }
    public int healAttack() {
        this.heal((int) (this.getDamage() * 1.5));
        return 0;
    }
    public int failedChargeAttack() {
        takeDamage(this.getDamage() / 2);
        //thi
        if (this.getCurrentHealth() <= 0) {
            die();
        }
        return angerAttack();
    }

    //minotaur is not affected by relic of bounciness (on purpose)
    @Override
    public String attack(Player player) {
        int damage = 0;
        int randomChoice = new Random().nextInt(9);
        String output = "<ERROR>";

        //make attacks return a string probably instead of the damage, do player.takeDamage in each attack? idk
        switch (randomChoice) {
            case 0 -> {
                damage = failedChargeAttack();
                output = "The minotaur tried to charge you, but fell over and smacked itself in the face! It doesn't seem very happy...";
            }
            case 1, 2 -> {
                damage = angerAttack();
                output = "The minotaur is getting upset!";
            }
            case 3, 4, 5 -> {
                damage = shriekAttack(player);
                output = "The minotaur shrieks, dealing " + damage + " damage and giving you a level of weakness!";
            }
            case 6, 7 -> {
                damage = chargeAttack();
                output = "The minotaur charges at you, goring you with its horns! You took " + damage + " damage!";
            }
            case 8 -> {
                damage = healAttack();
                output = "The minotaur took out a chunk of meat, healing " + (int) (this.getDamage() * 1.5) + " health!";
            }
        }

        player.takeDamage(damage);
        return output;
    }

}
