package main.item.relic;

import main.room.Room;

public class ForesightRelic extends Relic {
    public ForesightRelic() {
        this(1);
    }
    public ForesightRelic(double dropChance) {
        super("Relic of Foresight",
            "An interesting artifact that allows you to tell how many exits the next room has.",
            dropChance,
            RelicID.FORESIGHT);
    }

    public int findNumExits(Room room, int index) {
        return room.getExits().get(index).getNumExits();
    }
}
