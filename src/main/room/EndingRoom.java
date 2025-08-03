package main.room;

import main.Player;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

public class EndingRoom extends Room {
    public EndingRoom() {
        this.setId(9001);
        this.setNumExits(1);
        this.setDescription("""
            At last, your journey is over. A simple room, with just an old wooden staircase upwards.
            You can smell fresh air for the first time in a while. You don't hesitate to rush up the stairs, and find yourself
            in an old wine cellar. You head up further, finding yourself in a rustic bar.
            Before anyone has a chance to say anything, you dash out the door, raising your arms, feeling the sun on your shoulders.
            Freedom.""");
        this.setAppearance("You get the sense your journey is finally over.");
        this.setRoomsRequired(100);
        this.setActive(false);
        this.setSelectionWeight(1);
        this.setType(RoomType.SPECIAL);
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
