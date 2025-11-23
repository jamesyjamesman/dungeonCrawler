package main.item.weapon;

import main.entity.Player;
import main.item.Item;
import main.item.ItemType;

//should be abstract
public class Weapon extends Item {
    int damage;
    boolean equipped;
    public Weapon(String name, String description, int value, double dropChance, int shopWeight, int damage) {
        super(name, description, value, false, dropChance, shopWeight, ItemType.WEAPON);
        this.damage = damage;
        this.equipped = false;
    }

    @Override
    public String useItem(Player player) {
        if (this.isEquipped(player)) {
            player.setEquippedWeapon(null);
            this.equipped = false;
            return "The " + this.getName() + " was unequipped!";
        } else {
            player.setEquippedWeapon(this);
            this.equipped = true;
            return "The " + this.getName() + " was equipped!";
        }
    }

    public boolean isEquipped(Player player) {
        return this.equipped;
    }
    public int getDamage() {
        return this.damage;
    }
}
