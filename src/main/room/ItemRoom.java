package main.room;

import main.Player;
import main.initialization.ItemInit;
import main.item.Item;
import main.swing.SwingRenderer;

import java.util.ArrayList;
import java.util.Random;

public class ItemRoom extends Room {
    Item item;
    public ItemRoom() {
        super();
        this.item = null;
    }

    @Override
    public void completeRoomActions(Player player) {
        if (this.id == 12) {
            initializeItem();
        }
        super.completeRoomActions(player);
        player.itemPickup(this.item);
        SwingRenderer.UIUpdater(player);
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