package main.room;

import main.swing.SwingRenderer;
import main.item.Item;
import main.item.relic.Relic;
import main.Main;
import main.Player;

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
            SwingRenderer.appendMainLabelText(frame, "Would you like to ...look through the corpse? (y/n)", true);
        } else {
            SwingRenderer.appendMainLabelText(frame, "Would you like to take the " + this.item.getName() + "? (y/n)", true);
        }
        if (player.equippedRelicIndex("Relic of Curse Detection") > -1) {
            if (this.item instanceof Relic checkRelic && checkRelic.isCursed()) {
                    SwingRenderer.appendMainLabelText(frame, "Warning! The " + this.item.getName() + " is cursed!", false);
            }
        }
        String response = Main.yesOrNo(frame);
        if (response.equals("n")) {
            SwingRenderer.appendMainLabelText(frame, "You chose to forgo the loot...", false);
            return;
        }
        player.itemPickup(frame, this.item);
        player.checkInventory(frame);
    }
    public void setItem(Item item) {
        this.item = item;
    }
}
