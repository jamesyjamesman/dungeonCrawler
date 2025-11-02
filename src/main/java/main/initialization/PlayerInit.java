package main.initialization;

import main.entity.Player;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

public class PlayerInit {
    public static Player playerInit() {
        SwingRenderer.appendTextPane("First, you'll need to name your character!\nPlease enter your character's name.", true, ComponentType.PANE_MAIN);
        String playerName = "";
        while (playerName.isEmpty()) {
            playerName = SwingRenderer.getTempText();
        }
        Player player = new Player(playerName);
        SwingRenderer.appendLabelText("Your character's name is: " + player.getName(), true, ComponentType.LABEL_ERROR);
        return player;
    }
}
