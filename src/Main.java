import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the simulation! Enter your character's name.");
        Scanner lineScanner = new Scanner(System.in);
        String playerName = lineScanner.nextLine();
        Player newPlayer = new Player(playerName);
        System.out.println("Your character's name is:" + newPlayer.name);
    }
}