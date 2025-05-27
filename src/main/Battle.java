package main;

import main.enemy.Boss;
import main.enemy.Enemy;
import main.room.EnemyRoom;

import javax.swing.*;

public class Battle {

    public static void battleLoop(JFrame frame, Player player, EnemyRoom room) {
        System.out.println(Main.colorString("The battle has begun!", DialogueType.BATTLE));
        while (!room.getEnemies().isEmpty()) {
            String enemyString = Main.colorString("It's your turn! What would you like to attack?\n" + readEnemies(room, player), DialogueType.BATTLE);
            System.out.println(enemyString);

            player.printStatusLine();
            int enemyIndex = Main.responseHandler(frame, player, enemyString, 1, room.getEnemies().size()) - 1;

            Enemy enemy = room.getEnemies().get(enemyIndex);
            player.attack(enemy);
            boolean enemyIsDead = enemy.getCurrentHealth() == 0;
            if (enemyIsDead) {
                player.changeExperience(enemy.getExperienceDropped());
                player.checkLevelUp();
                System.out.println(Main.colorString("The " + enemy.getSpecies() + " died! You got " + enemy.getExperienceDropped() + " experience!", DialogueType.BATTLE));
                room.addDefeatedEnemies(enemy);
                room.removeEnemies(enemy);
                if (enemy instanceof Boss) {
                    ((Boss) enemy).death(frame, player);
                }
            }

            //enemies attack player
            for (int i = 0; i < room.getEnemies().size(); i++) {
                Enemy enemyAttacker = room.getEnemies().get(i);
                enemyAttacker.attack(player);
            }
        }
    }

    public static String readEnemies(EnemyRoom room, Player player) {
        String output = "";
        for (int i = 0; i < room.getEnemies().size(); i++) {
            Enemy enemy = room.getEnemies().get(i);
            String species = enemy.getSpecies();
            String speciesCapitalized = species.substring(0, 1).toUpperCase() + species.substring(1);
            output = output.concat((i+1) + ". " + speciesCapitalized);
            if (player.equippedRelicIndex("Relic of Enemy Information") > -1) {
                output = output.concat(" (" + enemy.getCurrentHealth() + "/" + enemy.getMaxHealth() + " HP)\n");
            } else {
                output = output.concat("\n");
            }
        }
        return output;
    }
}
