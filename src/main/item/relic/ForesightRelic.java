package main.item.relic;

import main.room.Room;

public class ForesightRelic extends Relic {
    public ForesightRelic() {
        setName("Relic of Foresight");
        setDescription("An interesting artifact that allows you to tell how many exits the next room has.");
    }

    public int findNumExits(Room room, int index) {
        return room.getExits().get(index).getNumExits();
    }
}
