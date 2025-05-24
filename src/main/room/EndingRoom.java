package main.room;

import main.Player;

public class EndingRoom extends Room {
    public EndingRoom() {
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        System.out.println("You survived long enough to escape! You win!");
        player.endStatistics();
    }
}
