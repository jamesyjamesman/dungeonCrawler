package main.room;

import main.Main;
import main.Player;
import main.item.Item;
import main.item.relic.Relic;
import main.item.relic.RelicID;
import main.swing.SwingRenderer;

import javax.swing.*;

public class ItemRoom extends Room {
    Item item;
    public ItemRoom() {
        super();
        this.item = null;
    }

    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);

        //this should be defined on the object itself but because the item isn't defined initially it crashes, which is not great.
        if (this.id == 10) {
            SwingRenderer.appendMainLabelText(frame, "Would you like to ...look through the corpse? (y/n)\n", true);
        } else {
            SwingRenderer.appendMainLabelText(frame, "Would you like to take the " + this.item.getName() + "? (y/n)", true);
        }
        if (player.equippedRelicIndex(RelicID.CURSE_DETECTION) > -1) {
            if (this.item instanceof Relic checkRelic && checkRelic.isCursed()) {
                    SwingRenderer.appendMainLabelText(frame, "Warning! The " + this.item.getName() + " is cursed!", false);
            }
        }
        boolean wantsItem = Main.parseResponseAsBoolean(frame);
        if (wantsItem) {
            player.itemPickup(frame, this.item);
            player.checkInventory(frame);
            return;
        }
        SwingRenderer.appendMainLabelText(frame, "You chose to forgo the loot...", false);
    }
    public void setItem(Item item) {
        this.item = item;
    }
}
