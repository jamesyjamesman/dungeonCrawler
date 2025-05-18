package main;

import main.enemy.Enemy;
import main.room.EnemyRoom;

public class Battle {

    public static void battleLoop(Player player, EnemyRoom room) {
        System.out.println("The battle has begun!");
        while (!room.getEnemies().isEmpty()) {
            System.out.println("It's your turn! What would you like to attack?");
            readEnemies(room);
            int enemyIndex = Main.responseHandler(player, 1, room.getEnemies().size()) - 1;
            Enemy enemy = room.getEnemies().get(enemyIndex);
            boolean enemyIsDead = player.attack(enemy);
            if (enemyIsDead) {
                System.out.println("The " + enemy.getSpecies() + " died!");
                room.addDefeatedEnemies(enemy);
                room.removeEnemies(enemy);
            }

            //enemies attack player
            for (int i = 0; i < room.getEnemies().size(); i++) {
                Enemy enemyAttacker = room.getEnemies().get(i);
                enemyAttacker.attack(player);
            }
        }
    }

    public static void readEnemies(EnemyRoom room) {
        for (int i = 0; i < room.getEnemies().size(); i++) {
            String species = room.getEnemies().get(i).getSpecies();
            String speciesCapitalized = species.substring(0, 1).toUpperCase() + species.substring(1);
            System.out.println((i+1) + ". " + speciesCapitalized);
        }
    }
}
