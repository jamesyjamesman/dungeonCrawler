package main.item.weapon;

import main.App;
import main.entity.Player;
import main.item.Item;
import main.item.ItemType;
import main.requests.ItemUseCase;

public abstract class Weapon extends Item {
    int damage;
    public Weapon(String name, String description, int value, double dropChance, int shopWeight, int damage) {
        super(name, description, value, false, dropChance, shopWeight, ItemType.WEAPON);
        this.damage = damage;
    }

    @Override
    public ItemUseCase useItem(Player player) {
        if (this.getEquipped()) {
            player.removeWeapon();
//            return "The " + this.getName() + " was unequipped!";
            return ItemUseCase.UNEQUIPPED;
        } else {
            if (player.setEquippedWeapon(this))
//                return "The " + this.getName() + " was equipped!";
                return ItemUseCase.EQUIPPED;
            else
                return ItemUseCase.WEAPON_ALREADY_EQUIPPED;
//                return "You must unequip your " + player.getEquippedWeapon().getName() + " first to equip your " + getName() + "!";
        }
    }

    public boolean getEquipped() {
        return App.INSTANCE.getPlayer().getEquippedWeapon() == this;
    }
    public int getDamage() {
        return this.damage;
    }
}
