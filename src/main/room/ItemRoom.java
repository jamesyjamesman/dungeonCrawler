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

        SwingRenderer.appendMainLabelText(frame, "Would you like to take the " + this.item.getName() + "? (y/n)", true);

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
