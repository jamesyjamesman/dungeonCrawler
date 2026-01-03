package main.item.armor;

import main.App;
import main.entity.Player;
import main.item.Item;
import main.item.ItemType;
import main.requests.ItemUseCase;

public abstract class Armor extends Item {
    private final int damageReduction;

    public Armor(String name, String description, int value, boolean stackable, double dropChance, int shopWeight, int damageReduction) {
        super(name, description, value, stackable, dropChance, shopWeight, ItemType.ARMOR);
        this.damageReduction = damageReduction;
    }

    @Override
    public ItemUseCase useItem(Player player) {
        if (this.getEquipped()) {
            player.setEquippedArmor(null);
            return ItemUseCase.UNEQUIPPED;
        } else {
            player.setEquippedArmor(this);
            return ItemUseCase.EQUIPPED;
        }
    }

    public int getDamageReduction() {
        return this.damageReduction;
    }
    public boolean getEquipped() {
        return App.INSTANCE.getPlayer().getEquippedArmor() == this;
    }
}
