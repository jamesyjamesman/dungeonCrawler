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
    public void useItem(Player player) {
        if (this.isEquipped(player)) {
            player.setEquippedWeapon(null);
        } else {
            player.setEquippedWeapon(this);
        }
    }

    public boolean isEquipped(Player player) {
        return this == player.getEquippedWeapon();
    }
    public int getDamage() {
        return damage;
    }
}
