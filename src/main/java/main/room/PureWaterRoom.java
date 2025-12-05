package main.room;

import main.Main;
import main.entity.Player;

public class PureWaterRoom extends Room {
    boolean fountainUsed;
    public PureWaterRoom(RoomBuilder<?> builder) {
        super(builder);
        this.fountainUsed = false;
    }

    @Override
    public void completeRoomActions(Player player) {
        this.fountainUsed = false;
        super.completeRoomActions(player);
    }

    //cleansing logic in Item.cleanseItem();
    public void setFountainUsed(boolean used) {
        this.fountainUsed = used;
    }
    public boolean getFountainUsed() {
        return this.fountainUsed;
    }
}
