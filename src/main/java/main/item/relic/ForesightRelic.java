package main.item.relic;

import main.entity.Player;
import main.room.Room;

public class ForesightRelic extends Relic {
    public ForesightRelic() {
        this(1);
    }
    public ForesightRelic(double dropChance) {
        super("Relic of Foresight",
            "This artifact helps you discern the future.",
            dropChance,
            RelicID.FORESIGHT);
    }

    public int findNumExits(Room room, int index) {
        return room.getExits().get(index).getNumExits();
    }

    @Override
    public String useRelic(Player player) {
        return "";
    }
}
