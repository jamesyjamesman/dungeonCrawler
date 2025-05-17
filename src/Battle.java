import java.util.Scanner;

public class Battle {

    public static void battleLoop(Player player, EnemyRoom room) {
        System.out.println("The battle has begun!");
        Scanner scanner = new Scanner(System.in);
        while (!room.enemies.isEmpty()) {
            System.out.println("It's your turn! What would you like to attack?");
            readEnemies(room);
            String response = Main.checkForCommands(player, scanner.nextLine());
            //can crash
            int enemyIndex = Integer.parseInt(response) - 1;
            Enemy enemy = room.enemies.get(enemyIndex);
            boolean enemyIsDead = player.attack(enemy);
            if (enemyIsDead) {
                System.out.println("The " + enemy.species + " died!");
                room.defeatedEnemies.add(enemy);
                room.enemies.remove(enemy);
            }

            //enemies attack player
            for (int i = 0; i < room.enemies.size(); i++) {
                Enemy enemyAttacker = room.enemies.get(i);
                enemyAttacker.attack(player);
            }
        }
    }

    public static void readEnemies(EnemyRoom room) {
        for (int i = 0; i < room.enemies.size(); i++) {
            String species = room.enemies.get(i).species;
            String speciesCapitalized = species.substring(0, 1).toUpperCase() + species.substring(1);
            System.out.println((i+1) + ". " + speciesCapitalized);
        }
    }
}
