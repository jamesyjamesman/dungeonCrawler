package main.room;

import main.Main;
import main.Player;
import main.initialization.ItemInit;
import main.item.Item;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class ItemRoom extends Room {
    Item item;
    public ItemRoom() {
        super();
        this.item = null;
    }

    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        if (this.id == 12) {
            initializeItem();
        }
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

    public void initializeItem() {
        ArrayList<Item> items = ItemInit.itemInit();
        Item item = items.get(new Random().nextInt(items.size()));
        this.setItem(item);
    }

    public void setItem(Item item) {
        this.item = item;
    }
}