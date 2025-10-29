package main.room;

import main.Main;
import main.entity.Player;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

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
        SwingRenderer.appendTextPane("""
                You sense this fountain has some purifying properties.
                You may place an item or relic in the fountain, if you'd like. Enter anything to continue.
                """, true, ComponentType.PANE_MAIN);
        Main.waitForResponse();
    }

    //cleansing logic in Item.cleanseItem();
    public void setFountainUsed(boolean used) {
        this.fountainUsed = used;
    }
    public boolean getFountainUsed() {
        return this.fountainUsed;
    }
}
