package main.entity.enemy;

import main.entity.Player;
import main.entity.Species;
import main.swing.SwingRenderer;

import java.util.Random;

//status attacks pierce through reflection. either put it on attack or leave it, cause its kinda interesting anyway
public class SickEnemy extends Enemy {
    public SickEnemy(Species species, int health, int damage, int experienceDropped) {
        super(species, health, damage, experienceDropped);
    }

    @Override
    public void attack(Player player) {
        switch (new Random().nextInt(5)) {
            case 0, 1 -> super.attack(player);
            case 2, 3 -> sneeze(player);
            case 4 -> grossAttack(player);
        }
    }

    public void sneeze(Player player) {
        int poisonAmount = new Random().nextInt(1,4);
        player.getCurrentStatuses().addPoison(poisonAmount);
        SwingRenderer.addHealthText("The " + speciesToStringLower() + " sneezed on you! You gained " + poisonAmount + " levels of poison.");
    }

    public void grossAttack(Player player) {
        int poisonAmount = new Random().nextInt(3,7);
        super.attack(player);
        player.getCurrentStatuses().addPoison(poisonAmount);
        SwingRenderer.addHealthText("...It was covered in snot. GROSS! You gained " + poisonAmount + " levels of poison.");
    }


}
