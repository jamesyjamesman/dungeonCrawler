import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the simulation!");
        System.out.println("You will be presented choices on where you would like to proceed. Choose carefully!");
        System.out.println("You can type 'status' into the console at any time to see your status.");

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

            for (int i = 0; i < currentRoom.numExits; i++) {
                currentRoom.exits.add(roomRandomizer(rooms));
                System.out.println((i + 1) + ". " + currentRoom.exits.get(i).appearance);
            }

            String promptResponse = commandListener(playerCharacter, promptScanner.nextLine());

            int response = Integer.parseInt(promptResponse) - 1;
            Room nextRoom = currentRoom.exits.get(response);
            currentRoom.exits.clear();
            currentRoom = nextRoom;
        }
        doDeathSequence(playerCharacter);
    }

    public static String commandListener(Player player, String input) {
        //this acts a little funky sometimes?
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            if (input.equals("status")) {
                player.checkStatus();
            } else {
                return input;
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
        // statistical summary (at least player stats, e.g. status).
        exit(0);
    }
}