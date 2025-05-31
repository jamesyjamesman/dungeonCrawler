package main.enemy;

import main.Main;
import main.Player;
import main.swing.SwingRenderer;
import main.item.relic.Relic;

import javax.swing.*;

public abstract class Boss extends Enemy {
    public Boss() {
    }

    public abstract Relic initializeBossRelic();

    public void death(JFrame frame, Player player) {
        Relic droppedRelic = initializeBossRelic();
        SwingRenderer.appendMainLabelText(frame, "Wow! You defeated the " + this.species + " boss! Lucky for you, it seems to have dropped something!\n" + "Would you like to take the " + droppedRelic.getName() + "? (y/n)");
        String response = Main.yesOrNo(frame);
        if (response.equals("y")) {
            player.itemPickup(frame, droppedRelic);
            player.checkInventory(frame);
        }
    }
}
