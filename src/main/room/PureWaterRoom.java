package main.room;

import main.Main;
import main.Player;
import main.swing.SwingRenderer;

public class PureWaterRoom extends Room {
    boolean fountainUsed;
    public PureWaterRoom() {
        this.fountainUsed = false;
        this.setId(9002);
        this.setNumExits(4);
        this.setDescription("You walk into the room, and see a fountain with flowing water. The water is almost luminescent.");
        this.setAppearance("You can hear rushing water, but that's about it.");
        this.setSelectionWeight(3);
        this.setType(RoomType.SPECIAL);
    }

    @Override
    public void completeRoomActions(Player player) {
        this.fountainUsed = false;
        super.completeRoomActions(player);
        SwingRenderer.appendMainLabelText("""
                You sense this fountain has some purifying properties.
                You may place an item or relic in the fountain, if you'd like. Enter anything to continue.
                """, true);
        Main.waitForResponse();
    }

    public void setFountainUsed(boolean used) {
        this.fountainUsed = used;
    }
    public boolean getFountainUsed() {
        return this.fountainUsed;
    }
}
