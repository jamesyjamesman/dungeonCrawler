import java.util.Scanner;

public class PlayerInit {
    public static Player playerInit() {
        System.out.println("First, you'll need to name your character! Please enter your character's name.");
        Scanner lineScanner = new Scanner(System.in);
        String playerName = lineScanner.nextLine();
        Player playerCharacter = new Player(playerName);
        System.out.println("Your character's name is: " + playerCharacter.name);
        return playerCharacter;
    }
}
