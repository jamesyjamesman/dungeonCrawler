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
        }
        player.takeDamage(damage);
    }

}
