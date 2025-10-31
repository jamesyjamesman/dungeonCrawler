package main.entity.enemy;

import main.entity.Player;
import main.entity.Species;
import main.item.Item;
import main.item.Loot;
import main.item.buff.DamageBuffItem;
import main.item.relic.DamageRelic;
import main.item.weapon.FieryGreatsword;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.ArrayList;
import java.util.Random;

public class DragonBoss extends Boss {
    boolean breathCharged = false;
    int defendCount = 0;
    public DragonBoss() {
        super(Species.DRAGON,120,10,500);

        ArrayList<Item> items = new ArrayList<>();
        items.add(new FieryGreatsword(0.5));
        items.add(new DamageBuffItem(0.6,3, 5));
        items.add(new DamageRelic(1, 5));

        setLoot(new Loot(300, items));
    }

    @Override
    public void attack(Player player) {
        if (this.defendCount > 0) {
            meditate();
            return;
        }
        if (this.breathCharged) {
            dragonBreath(player);
            return;
        }
        int randomNumber = new Random().nextInt(13);
        switch (randomNumber) {
            case 0, 1, 2 -> super.attack(player);
            case 3, 4, 5 -> dragonBreath(player);
            case 6, 7 -> deepBreath();
            case 8, 9 -> bite(player);
            case 10, 11 -> intimidate(player);
            case 12 -> {
                if (getCurrentHealth() <= getMaxHealth() / 2 ) {
                    cower();
                } else {
                    super.attack(player);
                }
            }
        }
    }

    public void dragonBreath(Player player) {
        int damage = getDamage();
        int maxFire = 8;
        if (this.breathCharged) {
            damage *= 3;
            maxFire *= 2;
            this.breathCharged = false;
        }
        SwingRenderer.addHealthText("The " + speciesToStringLower() + " blew fire over you, dealing " + damage + " damage and burning you!");
        player.takeDamage(damage);
        player.getCurrentStatuses().addFire(new Random().nextInt(maxFire/2, maxFire + 1));
    }

    public void deepBreath() {
        SwingRenderer.appendTextPane("The " + speciesToStringLower() + "takes a deep, rumbling breath...", false, ComponentType.PANE_MAIN);
        this.breathCharged = true;
    }

    public void bite(Player player) {
        int damage = getDamage() * 2;
        SwingRenderer.addHealthText("The " + speciesToStringLower() + " quickly swooped down and bit you, dealing " + damage + " damage!!");
        player.takeDamage(damage);
    }

    public void intimidate(Player player) {
        player.getCurrentStatuses().addWeakened(3);
        SwingRenderer.addHealthText("The " + speciesToStringLower() + " stands proud and tall. You lose hope you can win.");
    }

    public void cower() {
        SwingRenderer.appendTextPane("The " + speciesToStringLower() + " curls up, defending itself with its iron-hard wings.", false, ComponentType.PANE_MAIN);
        this.defendCount = new Random().nextInt(2, 5);
    }

    public void meditate() {
        heal(new Random().nextInt(7, 12));
        SwingRenderer.appendTextPane("The " + speciesToStringLower() + " nurses its wounds...", false, ComponentType.PANE_MAIN);
    }

    @Override
    public int takeDamage(int damage) {
        if (this.defendCount > 0) {
            this.defendCount--;
            SwingRenderer.appendTextPane("The attack bounced harmlessly off of the dragon's wings!", false, ComponentType.PANE_MAIN);
            return 0;
        }
        return super.takeDamage(damage);
    }
}
