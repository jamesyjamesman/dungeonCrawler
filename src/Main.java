import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the simulation! Enter your character's name.");
        Scanner lineScanner = new Scanner(System.in);
        String playerName = lineScanner.nextLine();
        Player newPlayer = new Player(playerName);
        System.out.println("Your character's name is: " + newPlayer.name);

        TrapRoom currentRoom = RoomInit.trapRoomInit();
        
        System.out.println("You find yourself in a cave. There's only one tunnel that leads anywhere.");
        System.out.println(currentRoom.appearance);
        System.out.println("Would you like to go into the next room? (y/n)");

        String answer = lineScanner.nextLine();
        if (answer.equals("y")) {
            System.out.println(currentRoom.description);
            System.out.println("You took " + currentRoom.damageDealt + " points of damage!");
            newPlayer.currentHealth -= currentRoom.damageDealt;
            System.out.println("You now have " + newPlayer.currentHealth + " health!");
        } else {
            System.out.println("Boring!");
        }

    }
}