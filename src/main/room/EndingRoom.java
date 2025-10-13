package main.room;

import main.Player;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

public class EndingRoom extends Room {
    public EndingRoom(RoomBuilder<?> builder) {
        super(builder);
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        SwingRenderer.appendTextPane("You survived long enough to escape! You win!", true, ComponentType.PANE_MAIN);
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
