package main;

import main.entity.Player;
import main.entity.enemy.Enemy;
import main.item.relic.RelicID;
import main.room.EnemyRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

public class Battle {

    public static void battleLoop(Player player, EnemyRoom room) {
        SwingRenderer.appendTextPane("\nThe battle has begun!\n", false, ComponentType.PANE_MAIN);
        App.INSTANCE.setState(App.State.BATTLE_START);
        while (!room.getEnemies().isEmpty()) {
            readEnemies(room, player);

            App.INSTANCE.setState(App.State.PLAYER_TURN);
            player.useRelics();
            player.statusHandler(true);
            int enemyIndex = Main.getIntegerResponse(player, 1, room.getEnemies().size()) - 1;
            SwingRenderer.appendTextPane("", true, ComponentType.PANE_MAIN);
            Enemy enemy = room.getEnemies().get(enemyIndex);
            player.attack(enemy);

            App.INSTANCE.setState(App.State.ENEMY_TURN);
            //enemies attack player
            for (int i = 0; i < room.getEnemies().size(); i++) {
                Enemy enemyAttacker = room.getEnemies().get(i);
                enemyAttacker.attack(player);
            }
        }
        SwingRenderer.appendTextPane("The enemies were defeated!", true, ComponentType.PANE_MAIN);
    }

    public static void readEnemies(EnemyRoom room, Player player) {
        for (int i = 0; i < room.getEnemies().size(); i++) {
            Enemy enemy = room.getEnemies().get(i);
            String species = enemy.speciesToString();
            String speciesCapitalized = species.charAt(0) + species.substring(1).toLowerCase();
            String output = (i+1) + ". " + speciesCapitalized;
            if (player.equippedRelicIndex(RelicID.ENEMY_INFORMATION) > -1) {
                output = output.concat(" (" + enemy.getCurrentHealth() + "/" + enemy.getMaxHealth() + " HP)\n");
            } else {
                output = output.concat("\n");
            }
            SwingRenderer.addEnemyLabel(output, player, enemy, i);
        }
    }
}
