package main.room;

import main.item.Item;

public class ItemRoomBuilder<T extends ItemRoomBuilder<T>> extends RoomBuilder<T> {
    Item item;

    public T item(Item item) {
        this.item = item;
        return self();
    }

    @Override
    public ItemRoom build() {
        return new ItemRoom(this);
    }
}
