package main;

import main.enemy.Enemy;
import main.item.relic.RelicID;
import main.room.EnemyRoom;
import main.swing.SwingRenderer;

public class Battle {

    public static void battleLoop(Player player, EnemyRoom room) {
        SwingRenderer.appendMainLabelText("\nThe battle has begun!\n", false);
        while (!room.getEnemies().isEmpty()) {
            readEnemies(room, player);

            int enemyIndex = Main.getIntegerResponse(player, 1, room.getEnemies().size()) - 1;
            SwingRenderer.appendMainLabelText("", true);
            Enemy enemy = room.getEnemies().get(enemyIndex);
            player.attack(enemy);
            //this should just be in takeDamage but this is fine for now
            if (enemy.getCurrentHealth() <= 0) {
                enemy.death(player, room);
            }

            //enemies attack player
            for (int i = 0; i < room.getEnemies().size(); i++) {
                Enemy enemyAttacker = room.getEnemies().get(i);
                enemyAttacker.attack(player);
            }
        }
        SwingRenderer.appendMainLabelText("The enemies were defeated!", true);
    }

    public static void readEnemies(EnemyRoom room, Player player) {
        for (int i = 0; i < room.getEnemies().size(); i++) {
            Enemy enemy = room.getEnemies().get(i);
            String species = enemy.getSpecies();
            String speciesCapitalized = species.substring(0, 1).toUpperCase() + species.substring(1);
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
