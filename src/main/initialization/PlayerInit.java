package main.initialization;

import main.swing.ComponentType;
import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;

public class PlayerInit {
    public static Player playerInit(JFrame frame) {
        SwingRenderer.appendMainLabelText(frame, "First, you'll need to name your character!\nPlease enter your character's name.", true);
        String playerName = "";
        while (playerName.isEmpty()) {
            playerName = SwingRenderer.getTempText(frame);
        }
        Player player = new Player(playerName);
        SwingRenderer.changeLabelText(frame, "Your character's name is: " + player.getName(), ComponentType.LABEL_ERROR);
        return player;
    }
}
