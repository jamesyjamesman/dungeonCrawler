package main.entity.enemy;

import main.entity.Player;
import main.entity.Species;
import main.item.Item;
import main.item.Loot;
import main.item.buff.DamageBuffItem;
import main.item.relic.DamageRelic;
import main.item.weapon.FieryGreatsword;

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
    public String attack(Player player) {
        if (this.defendCount > 0) {
            return meditate();
        }
        if (this.breathCharged) {
            return dragonBreath(player);
        }
        int randomNumber = new Random().nextInt(13);
        return switch (randomNumber) {
            case 0, 1, 2 -> super.attack(player);
            case 3, 4, 5 -> dragonBreath(player);
            case 6, 7 -> deepBreath();
            case 8, 9 -> bite(player);
            case 10, 11 -> intimidate(player);
            case 12 -> {
                if (getCurrentHealth() <= getMaxHealth() / 2 ) {
                    yield cower();
                } else {
                    yield super.attack(player);
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + randomNumber);
        };
    }

    public String dragonBreath(Player player) {
        int damage = getDamage();
        int maxFire = 8;
        if (this.breathCharged) {
            damage *= 3;
            maxFire *= 2;
            this.breathCharged = false;
        }
        int actualFire = new Random().nextInt(maxFire/2, maxFire + 1);
        player.takeDamage(damage);
        player.getCurrentStatuses().addFire(actualFire);
        return "OW! You took " + damage + " damage and got " + actualFire + " levels of fire from the dragon's fire breath!";
    }

    public String deepBreath() {
        this.breathCharged = true;
        return "The dragon takes a deep breath...";
    }

    public String bite(Player player) {
        int damage = getDamage() * 2;
        player.takeDamage(damage);
        return "The dragon quickly swoops down and bites you, dealing " + damage + " damage!";
    }

    public String intimidate(Player player) {
        player.getCurrentStatuses().addWeakened(3);
        return "The dragon stands tall and proud. You lose hope you can win.";
    }

    public String cower() {
        this.defendCount = new Random().nextInt(2, 5);
        return "The dragon wraps itself in its iron-hard wings.";
    }

    public String meditate() {
        int healAmount = new Random().nextInt(7, 12);
        heal(healAmount);
        return "The dragon nurses its wounds, healing " + healAmount + " health.";
    }

    @Override
    public int takeDamage(int damage) {
        if (this.defendCount > 0) {
            this.defendCount--;
            return 0;
        }
        return super.takeDamage(damage);
    }
}
