package main.enemy;

import main.Main;
import main.Player;
import main.item.relic.Relic;
import main.room.EnemyRoom;
import main.swing.SwingRenderer;

import javax.swing.*;

public abstract class Boss extends Enemy {
    public Boss() {
    }

    public abstract Relic initializeBossRelic();

    @Override
    public void death(JFrame frame, Player player, EnemyRoom enemyRoom) {
        super.death(frame, player, enemyRoom);
        Relic droppedRelic = initializeBossRelic();
        SwingRenderer.appendMainLabelText("Wow! You defeated the " + this.species + " boss! Lucky for you, it seems to have dropped something!\n" + "Would you like to take the " + droppedRelic.getName() + "? (y/n)", false);
        boolean wantsPickup = Main.parseResponseAsBoolean();
        if (wantsPickup) {
            player.itemPickup(droppedRelic);
            player.checkInventory();
        }
    }
}
