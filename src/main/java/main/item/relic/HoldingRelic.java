package main.item.relic;

import main.entity.Player;

public class HoldingRelic extends Relic {
    public HoldingRelic() {
        this(1);
    }
    public HoldingRelic(double dropChance) {
        super("Relic of Holding",
                "A strange relic that somehow fits many things in a small amulet.",
                dropChance,
                RelicID.HOLDING);
    }

    @Override
    public String useRelic(Player player) {
        return "";
    }

    @Override
    public String useItem(Player player) {
        if (this.isEquipped(player)) {
            int itemsOverCapacity = player.calculateInventorySize() - (player.getInventoryCap() - 10);
            if (itemsOverCapacity > 0) {
                return "You have too many items to unequip this!";
            }
            if (player.unequipRelic(this)) {
                player.changeInventoryCap(-10);
                return "You unequipped the " + this.getName() + "!";
            }
            return "Your inventory is full!";
        } else {
            if (player.equipRelic(this)) {
                player.changeInventoryCap(10);
                return "You equipped the " + this.getName() + "!";
            }
            return "Your relic pouch is full!";
        }
    }
}
