package main.enemy;

import main.Main;
import main.Player;
import main.room.EnemyRoom;
import main.swing.SwingRenderer;
import main.item.relic.Relic;

import javax.swing.*;

public abstract class Boss extends Enemy {
    public Boss() {
    }

    public abstract Relic initializeBossRelic();

    @Override
    public void death(JFrame frame, Player player, EnemyRoom enemyRoom) {
        super.death(frame, player, enemyRoom);
        Relic droppedRelic = initializeBossRelic();
        SwingRenderer.appendMainLabelText(frame, "Wow! You defeated the " + this.species + " boss! Lucky for you, it seems to have dropped something!\n" + "Would you like to take the " + droppedRelic.getName() + "? (y/n)", false);
        boolean wantsPickup = Main.parseResponseAsBoolean(frame);
        if (wantsPickup) {
            player.itemPickup(frame, droppedRelic);
            player.checkInventory(frame);
        }
    }
}
