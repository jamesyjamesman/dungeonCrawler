package main.room;

import main.LabelType;
import main.Player;
import main.SwingRenderer;

import javax.swing.JFrame;

public class EndingRoom extends Room {
    public EndingRoom() {
    }

    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);
        SwingRenderer.changeLabelText(frame, "You survived long enough to escape! You win!", LabelType.MAIN);
//        player.endStatistics(frame);
        while (true) {

        }
    }
}
