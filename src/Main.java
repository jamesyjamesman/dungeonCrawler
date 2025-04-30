import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the simulation! Enter your character's name.");
        Scanner lineScanner = new Scanner(System.in);
        String playerName = lineScanner.nextLine();
        Player newPlayer = new Player(playerName);
        System.out.println("Your character's name is: " + newPlayer.name);

        Room newRoom = new Room();
        newRoom.description = "Coolbeans";
        System.out.println("Room description: " + newRoom.description);

        TrapRoom newTrapRoom = new TrapRoom();
        newTrapRoom.description = "Oh no! There's a bunch of stalagmites that fall from the ceiling when you enter!";
        System.out.println("Would you like to go into the next room? (y/n)");
        String answer = lineScanner.nextLine();
        if (answer.equals("y")) {
            System.out.println(newTrapRoom.description);
            System.out.println("You took " + newTrapRoom.damageDealt + " points of damage!");
            newPlayer.currentHealth -= newTrapRoom.damageDealt;
            System.out.println("You now have " + newPlayer.currentHealth + " health!");
        } else {
            System.out.println("Boring!");
        }

    }
}