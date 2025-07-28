package main.item.relic;

import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;

public class HoldingRelic extends Relic {
    public HoldingRelic() {
        setName("Relic of Holding");
        setType(RelicID.HOLDING);
        setDescription("A strange relic that somehow fits many things in a small amulet.");
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        if (this.isEquipped(player)) {
            int itemsOverCapacity = player.calculateInventorySize() - (player.getInventoryCap() - 10);
            if (itemsOverCapacity > 0) {
                SwingRenderer.appendMainLabelText(frame, "You have too many items to remove this relic!\n" + "Remove " + itemsOverCapacity + " items from your inventory to remove this relic.", false);
                SwingRenderer.UIUpdater(frame, player);
                return;
            }
            if (player.unequipRelic(frame, this)) {
            player.changeInventoryCap(-10);
            }
        }
        else {
            if (player.equipRelic(frame,this)) {
            player.changeInventoryCap(10);
            }
        }
        SwingRenderer.UIUpdater(frame, player);
    }
}
