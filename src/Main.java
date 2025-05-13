import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the simulation!");
        System.out.println("You will be presented choices on where you would like to proceed. Choose carefully!");
        System.out.println("You can type commands into the console at any time. Try 'help' to see a list of commands.");

        Player playerCharacter = PlayerInit.playerInit();
        ArrayList<Room> rooms = RoomInit.roomInit();

        Room firstRoom = rooms.getFirst();

        gameLoop(playerCharacter, firstRoom, rooms);
    }

    public static void gameLoop(Player playerCharacter, Room currentRoom, ArrayList<Room> rooms) {
        Scanner promptScanner = new Scanner(System.in);
        while (playerCharacter.currentHealth > 0) {
            currentRoom.completeRoomActions(playerCharacter);

            System.out.println("Where would you like to go?");
            System.out.println("You see " + currentRoom.numExits + " exit" + pluralChecker(currentRoom.numExits) + ".");

            for (int i = 0; i < currentRoom.numExits; i++) {
                currentRoom.exits.add(roomRandomizer(rooms));
                System.out.println((i + 1) + ". " + currentRoom.exits.get(i).appearance);
            }

            while (true) {
                String promptResponse = inputHelper(playerCharacter, promptScanner.nextLine());
                int response;
                Room nextRoom;
                try {
                    response = Integer.parseInt(promptResponse) - 1;
                } catch(NumberFormatException e) {
                    System.out.println("Invalid response.");
                    continue;
                }
                try {
                    nextRoom = currentRoom.exits.get(response);
                } catch(IndexOutOfBoundsException e) {
                    System.out.println("Invalid exit!");
                    continue;
                }
                currentRoom.exits.clear();
                currentRoom = nextRoom;
                break;
            }
        }
        doDeathSequence(playerCharacter);
    }

    public static String inputHelper(Player player, String input) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            switch (input) {
                case "status" -> player.checkStatus();
                case "help" -> commandList();
                case "inventory" -> player.checkInventory(false);
                default -> {
                    return input;
                }
            }
            input = promptScanner.nextLine();
        }
    }
    public static Room roomRandomizer(ArrayList<Room> rooms) {
        return rooms.get(new Random().nextInt(rooms.size()));
        //room should be removed from list, or at least prevented from being put more than once in a particular room.
    }

    public static void doDeathSequence(Player player) {
        System.out.println("\"Ack! It's too much for me!\" " + player.name + " exclaims.");
        System.out.println(player.name + " falls to their knees... then to the ground.");
        System.out.println("GAME OVER!");
        System.out.println();
        endStatistics(player);
        exit(0);
    }

    public static void commandList() {
        System.out.println("List of commands:");
        System.out.println("help: checks this command.");
        System.out.println("status: checks player's status and statistics.");
        System.out.println("inventory: shows the contents of the player's inventory.");
    }

    public static String pluralChecker(int numThings) {
        if (numThings == 1) {
            return "";
        } else {
            return "s";
        }
    }

    public static void endStatistics(Player player) {
        System.out.println("Player statistics:");
        System.out.println("Maximum health: " + player.maxHealth);
        System.out.println("Inventory:");
        player.checkInventory(true);
    }
}