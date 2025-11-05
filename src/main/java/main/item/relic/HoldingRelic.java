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
    public void useItem(Player player) {
        if (this.isEquipped(player)) {
            int itemsOverCapacity = player.calculateInventorySize() - (player.getInventoryCap() - 10);
            if (itemsOverCapacity > 0) {
                return;
            }
            if (player.unequipRelic(this)) {
            player.changeInventoryCap(-10);
            }
        }
        else {
            if (player.equipRelic(this)) {
            player.changeInventoryCap(10);
            }
        }
    }
}
