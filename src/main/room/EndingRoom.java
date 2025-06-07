package main.room;

import main.swing.LabelType;
import main.Player;
import main.swing.SwingRenderer;

import javax.swing.JFrame;

public class EndingRoom extends Room {
    public EndingRoom() {
    }

    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);
        SwingRenderer.appendMainLabelText(frame, "You survived long enough to escape! You win!", true);
        while (true) {

        }
    }
}
