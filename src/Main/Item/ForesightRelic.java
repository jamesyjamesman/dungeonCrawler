package Main.Item;

import Main.Room.Room;

public class ForesightRelic extends Relic {
    public ForesightRelic() {
        this.name = "Relic of Foresight";
        this.description = "An interesting artifact that allows you to tell how many exits the next room has.";
    }

    public int findNumExits(Room room, int index) {
        return room.getExits().get(index).getNumExits();
    }
}
