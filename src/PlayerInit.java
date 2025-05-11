import java.util.Scanner;

public class PlayerInit {
    public static Player playerInit() {
        System.out.println("First, you'll need to name your character!\nPlease enter your character's name.");
        Scanner lineScanner = new Scanner(System.in);
        String playerName = lineScanner.nextLine();
        Player playerCharacter = new Player(playerName);
        if (playerCharacter.name.equals("help")) {
            System.out.println("Well, not right now...");
        }
        System.out.println("Your character's name is: " + playerCharacter.name);
        return playerCharacter;
    }
}
