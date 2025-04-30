import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the simulation! Enter your character's name.");
        Scanner lineScanner = new Scanner(System.in);
        String playerName = lineScanner.nextLine();
        Player newPlayer = new Player(playerName);
        System.out.println("Your character's name is: " + newPlayer.name);

        ArrayList<TrapRoom> trapRooms = RoomInit.trapRoomInit();
        TrapRoom firstOption = trapRooms.get(0);

        ArrayList<EnemyRoom> enemyRooms = RoomInit.enemyRoomInit();
        EnemyRoom secondOption = enemyRooms.get(0);

        System.out.println("You find yourself in a cave. There's two tunnels.");
        System.out.println("For the first option, " + firstOption.appearance);
        System.out.println("For the second option, " + secondOption.appearance);
        System.out.println("Would you like to go into the first room? (y/n)");

        String answer = lineScanner.nextLine();
        if (answer.equals("y")) {
            System.out.println(firstOption.description);
            System.out.println("You took " + firstOption.damageDealt + " points of damage!");
            newPlayer.currentHealth -= firstOption.damageDealt;
            System.out.println("You now have " + newPlayer.currentHealth + " health!");
        } else {
            System.out.println(secondOption.description);
            System.out.println(secondOption.battleInitiationMessage);
            System.out.println("You took " + secondOption.enemies.get(0).damage + " points of damage!");
        }

    }
}