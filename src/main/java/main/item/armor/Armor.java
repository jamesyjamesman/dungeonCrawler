package main.item.armor;

import main.entity.Player;
import main.item.Equippable;
import main.item.Item;
import main.item.ItemType;
import main.requests.ItemUseCase;

public abstract class Armor extends Item implements Equippable {
    private final int damageReduction;

    public Armor(String name, String description, int value, boolean stackable, double dropChance, int shopWeight, int damageReduction) {
        super(name, description, value, stackable, dropChance, shopWeight, ItemType.ARMOR);
        this.damageReduction = damageReduction;
    }

    @Override
    public ItemUseCase equip(Player player) {
        player.setEquippedArmor(this);
        return ItemUseCase.EQUIPPED;
    }

    @Override
    public ItemUseCase unequip(Player player) {
        if (this.isEquipped(player)) {
            player.setEquippedArmor(null);
            return ItemUseCase.UNEQUIPPED;
        } else
            throw new IllegalStateException("Armor is not equipped!");
    }

    @Override
    public boolean isEquipped(Player player) {
        return player.getEquippedArmor() == this;
    }

    @Override
    public ItemUseCase useItem(Player player) {
        return ItemUseCase.NO_GENERIC_USE;
    }

    public int getDamageReduction() {
        return this.damageReduction;
    }
}
