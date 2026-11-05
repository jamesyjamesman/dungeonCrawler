package main.item.weapon;

import main.entity.Player;
import main.item.Equippable;
import main.item.Item;
import main.item.ItemType;
import main.requests.ItemUseCase;

public abstract class Weapon extends Item implements Equippable {
    int damage;
    public Weapon(String name, String description, int value, double dropChance, int shopWeight, int damage) {
        super(name, description, value, false, dropChance, shopWeight, ItemType.WEAPON);
        this.damage = damage;
    }

    @Override
    public ItemUseCase equip(Player player) {
        player.setEquippedWeapon(this);
        return ItemUseCase.EQUIPPED;
    }

    @Override
    public ItemUseCase unequip(Player player) {
        if (this.isEquipped(player)) {
            player.removeWeapon();
            return ItemUseCase.UNEQUIPPED;
        } else {
            throw new IllegalStateException("Weapon is not equipped!");
        }
    }

    @Override
    public boolean isEquipped(Player player) {
        return player.getEquippedWeapon() == this;
    }

    @Override
    public ItemUseCase useItem(Player player) {
        return ItemUseCase.NO_GENERIC_USE;
    }

    public int getDamage() {
        return this.damage;
    }
}
