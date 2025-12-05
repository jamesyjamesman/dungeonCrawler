package main.room;

import main.entity.Player;

public class EndingRoom extends Room {
    public EndingRoom(RoomBuilder<?> builder) {
        super(builder);
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
