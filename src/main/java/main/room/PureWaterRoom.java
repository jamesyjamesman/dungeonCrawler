package main.room;

import main.App;
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

    // todo if fountain used uhh you hit your head and take like 1-3 damage (cuz water gone)
    public boolean selfFountainize() {
        Player player = App.INSTANCE.getPlayer();
        if (player.getCurrentStatuses().getFire() > 0) {
            player.getCurrentStatuses().setFire(0);
            return true;
        } else {
            return false;
        }
    }

    //cleansing logic in Item.cleanseItem();
    public void setFountainUsed(boolean used) {
        this.fountainUsed = used;
    }
    public boolean getFountainUsed() {
        return this.fountainUsed;
    }
}
