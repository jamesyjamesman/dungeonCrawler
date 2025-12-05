package main.entity.enemy;

import main.entity.Player;
import main.entity.Species;

import java.util.Random;

//status attacks pierce through reflection. either put it on attack or leave it, cause its kinda interesting anyway
public class SickEnemy extends Enemy {
    public SickEnemy(Species species, int health, int damage, int experienceDropped, int minimumLevel) {
        super(species, health, damage, experienceDropped, minimumLevel);
    }

    @Override
    public String attack(Player player) {
        return switch (new Random().nextInt(5)) {
            case 0, 1 -> super.attack(player);
            case 2, 3 -> sneeze(player);
            case 4 -> grossAttack(player);
            //intellij is making me do this???
            default -> throw new IllegalStateException("Unexpected value!");
        };
    }

    public String sneeze(Player player) {
        int poisonAmount = new Random().nextInt(1,4);
        player.getCurrentStatuses().addPoison(poisonAmount);
        return "Gross! The " + this.speciesToStringLower() + " sneezed on you, giving you " + poisonAmount + " levels of poison!";
    }

    public String grossAttack(Player player) {
        int poisonAmount = new Random().nextInt(3,7);
        String normal = super.attack(player);
        player.getCurrentStatuses().addPoison(poisonAmount);
        return "...EW! The " + this.speciesToStringLower() + " sneezed on their weapon, then hit you with it! You got " + poisonAmount + " levels of poison! " + normal;
    }
}
