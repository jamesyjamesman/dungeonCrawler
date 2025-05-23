package main.item.relic;

import main.Player;

public class HoldingRelic extends Relic {
    public HoldingRelic() {
        setName("Relic of Holding");
        setDescription("A strange relic that somehow fits many things in a small amulet.");
    }

    @Override
    public void useItem(Player player) {
        if (this.equipped) {
            int itemsOverCapacity = player.calculateInventorySize() - (player.getInventoryCap() - 10);
            if (itemsOverCapacity > 0) {
                System.out.println("You have too many items to remove this relic!");
                System.out.println("Remove " + itemsOverCapacity + " items from your inventory to remove this relic.");
                return;
            }
            player.unequipRelic(this);
            player.changeInventoryCap(-10);
        }
        else {
            player.equipRelic(this);
            player.changeInventoryCap(10);
        }
    }
}
