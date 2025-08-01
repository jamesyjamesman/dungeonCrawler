package main.initialization;

import main.Player;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

public class PlayerInit {
    public static Player playerInit() {
        SwingRenderer.appendMainLabelText("First, you'll need to name your character!\nPlease enter your character's name.", true);
        String playerName = "";
        while (playerName.isEmpty()) {
            playerName = SwingRenderer.getTempText();
        }
        Player player = new Player(playerName);
        SwingRenderer.changeLabelText("Your character's name is: " + player.getName(), ComponentType.LABEL_ERROR);
        return player;
    }
}
