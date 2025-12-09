package main.item.armor;

import main.entity.Player;
import main.item.Item;
import main.item.ItemType;

public abstract class Armor extends Item {
    private final int damageReduction;
    private boolean equipped;

    public Armor(String name, String description, int value, boolean stackable, double dropChance, int shopWeight, int damageReduction) {
        super(name, description, value, stackable, dropChance, shopWeight, ItemType.ARMOR);
        this.damageReduction = damageReduction;
    }

    @Override
    public String useItem(Player player) {
        if (this.getEquipped()) {
            player.setEquippedArmor(null);
            this.equipped = false;
            return "The " + this.getName() + " was unequipped!";
        } else {
            player.setEquippedArmor(this);
            this.equipped = true;
            return "The " + this.getName() + " was equipped!";
        }
    }

    public int getDamageReduction() {
        return this.damageReduction;
    }
    public boolean getEquipped() {
        return this.equipped;
    }
}
