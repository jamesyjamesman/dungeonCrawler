package main.room;

import main.Player;

import javax.swing.JFrame;

import static java.lang.System.exit;

public class EndingRoom extends Room {
    public EndingRoom() {
    }

    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);
        System.out.println("You survived long enough to escape! You win!");
        player.endStatistics(frame);
        exit(0);
    }
}
