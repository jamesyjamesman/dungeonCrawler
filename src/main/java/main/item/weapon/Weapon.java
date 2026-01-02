package main.item.weapon;

import main.App;
import main.entity.Player;
import main.item.Item;
import main.item.ItemType;

public abstract class Weapon extends Item {
    int damage;
    public Weapon(String name, String description, int value, double dropChance, int shopWeight, int damage) {
        super(name, description, value, false, dropChance, shopWeight, ItemType.WEAPON);
        this.damage = damage;
    }

    @Override
    public String useItem(Player player) {
        if (this.getEquipped()) {
            player.removeWeapon();
            return "The " + this.getName() + " was unequipped!";
        } else {
            if (player.setEquippedWeapon(this))
                return "The " + this.getName() + " was equipped!";
            else
                return "You must unequip your " + player.getEquippedWeapon().getName() + " first to equip your " + getName() + "!";
        }
    }

    public boolean getEquipped() {
        return App.INSTANCE.getPlayer().getEquippedWeapon() == this;
    }
    public int getDamage() {
        return this.damage;
    }
}
