package main.item.relic;

import main.entity.Player;
import main.requests.ItemUseCase;

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
    public ItemUseCase useItem(Player player) {
        if (this.isEquipped(player)) {
            int itemsOverCapacity = player.calculateInventorySize() - (player.getInventoryCap() - 10);
            if (itemsOverCapacity > 0) {
                return ItemUseCase.INVENTORY_OVERFLOW;
            }
            ItemUseCase unequipStatus = player.unequipRelic(this);
            if (unequipStatus == ItemUseCase.UNEQUIPPED) {
                player.changeInventoryCap(-10);
            }
            return unequipStatus;
        } else {
            if (player.equipRelic(this)) {
                player.changeInventoryCap(10);
                return ItemUseCase.EQUIPPED;
            }
            return ItemUseCase.POUCH_FULL;
        }
    }
}
