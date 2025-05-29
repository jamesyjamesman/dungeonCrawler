package main.initialization;

import main.LabelType;
import main.Player;
import main.SwingRenderer;

import javax.swing.*;

public class PlayerInit {
    public static Player playerInit(JFrame frame) {
        SwingRenderer.changeLabelText(frame, "First, you'll need to name your character!\nPlease enter your character's name.", LabelType.MAIN);
        String playerName = "";
        while (playerName.isEmpty()) {
            playerName = SwingRenderer.getTempText(frame);
        }
        Player player = new Player(playerName);
        if (player.getName().equals("help")) {
            System.out.println("Well, not right now...");
        }
        System.out.println("Your character's name is: " + player.getName());
        return player;
    }
}
