package main;

import main.entity.Player;
import main.entity.enemy.Enemy;
import main.item.relic.RelicID;
import main.room.EnemyRoom;

public class Battle {

    public static void battleLoop(Player player, EnemyRoom room) {
        App.INSTANCE.setState(App.State.BATTLE_START);
        while (!room.getEnemies().isEmpty()) {
            readEnemies(room, player);

            App.INSTANCE.setState(App.State.PLAYER_TURN);
            player.useRelics();
            player.statusHandler(true);
            //todo hardcoded
            Enemy enemy = room.getEnemies().get(1);
            player.attack(enemy);

            App.INSTANCE.setState(App.State.ENEMY_TURN);
            //enemies attack player
            for (int i = 0; i < room.getEnemies().size(); i++) {
                Enemy enemyAttacker = room.getEnemies().get(i);
                enemyAttacker.attack(player);
            }
        }
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
        }
    }
}
