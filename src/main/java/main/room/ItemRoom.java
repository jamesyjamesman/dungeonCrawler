package main.room;

import main.entity.Player;
import main.initialization.ItemInit;
import main.item.Item;

import java.util.ArrayList;
import java.util.Random;

public class ItemRoom extends Room {
    private Item item;
    public ItemRoom(ItemRoomBuilder<?> builder) {
        super(builder);
        this.item = builder.item != null ? builder.item : null;
    }

    @Override
    public void completeRoomActions(Player player) {
        //todo itemRoom flag boolean of "random" and an ArrayList<Item> of allowed items, similar logic to enemyroom
        if (this.getId() == 5003) {
            initializeItem();
        }
        super.completeRoomActions(player);
    }

    public void initializeItem() {
        ArrayList<Item> items = ItemInit.itemInit();
        Item item = items.get(new Random().nextInt(items.size()));
        this.setItem(item);
    }

    public void setItem(Item item) {
        this.item = item;
    }

    //for serialization
    public Item getItem() {
        return this.item;
    }
}