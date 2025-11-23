package main.item.weapon;

import main.entity.Player;
import main.item.Item;

//should be abstract
public class Weapon extends Item {
    int damage;
    public Weapon(String name, String description, int value, double dropChance, int shopWeight, int damage) {
        super(name, description, value, false, dropChance, shopWeight);
        this.damage = damage;
    }

    @Override
    public String useItem(Player player) {
        if (this.isEquipped(player)) {
            player.setEquippedWeapon(null);
            return "The " + this.getName() + " was unequipped!";
        } else {
            player.setEquippedWeapon(this);
            return "The " + this.getName() + " was equipped!";
        }
    }

    public boolean isEquipped(Player player) {
        return this == player.getEquippedWeapon();
    }
    public int getDamage() {
        return this.damage;
    }
}
