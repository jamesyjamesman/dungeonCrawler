package main;

import main.enemy.Boss;
import main.enemy.Enemy;
import main.room.EnemyRoom;

public class Battle {

    public static void battleLoop(Player player, EnemyRoom room) {
        System.out.println("The battle has begun!");
        while (!room.getEnemies().isEmpty()) {
            System.out.println("It's your turn! What would you like to attack?");
            readEnemies(room, player);
            int enemyIndex = Main.responseHandler(player, 1, room.getEnemies().size()) - 1;
            Enemy enemy = room.getEnemies().get(enemyIndex);
            player.attack(enemy);
            boolean enemyIsDead = enemy.getCurrentHealth() == 0;
            if (enemyIsDead) {
                System.out.println("The " + enemy.getSpecies() + " died!");
                room.addDefeatedEnemies(enemy);
                room.removeEnemies(enemy);
                if (enemy instanceof Boss) {
                    ((Boss) enemy).death(player);
                }
            }

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
            String species = enemy.getSpecies();
            String speciesCapitalized = species.substring(0, 1).toUpperCase() + species.substring(1);
            System.out.print((i+1) + ". " + speciesCapitalized);
            if (player.equippedRelicIndex("Relic of Enemy Information") > -1) {
                System.out.println(" (" + enemy.getCurrentHealth() + "/" + enemy.getMaxHealth() + " HP)");
            } else {
                System.out.println();
            }
        }
    }
}
