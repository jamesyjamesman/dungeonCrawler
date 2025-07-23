package main.room;

import main.Main;
import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;

public class PureWaterRoom extends Room {
    boolean fountainUsed;
    public PureWaterRoom() {
        this.fountainUsed = false;
    }

    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);
        SwingRenderer.appendMainLabelText(frame, """
                You sense this fountain has some purifying properties.
                You may place an item or relic in the fountain, if you'd like. Enter anything to continue.
                """, true);
        Main.waitForResponse(frame);
    }

    public void setFountainUsed(boolean used) {
        this.fountainUsed = used;
    }
    public boolean getFountainUsed() {
        return this.fountainUsed;
    }
}
