import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the simulation!");

        Player playerCharacter = PlayerInit.playerInit();
        ArrayList<Room> rooms = RoomInit.roomInit();

        Room firstRoom = rooms.getFirst();

        gameLoop(playerCharacter, firstRoom, rooms);
    }

    public static void gameLoop(Player playerCharacter, Room currentRoom, ArrayList<Room> rooms) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            currentRoom.completeRoomActions(playerCharacter);

            System.out.println("Where would you like to go?");

            for (int i = 0; i < currentRoom.numExits; i++) {
                currentRoom.exits.add(roomRandomizer(rooms));
                System.out.println((i + 1) + ". " + currentRoom.exits.get(i).appearance);
            }

            //this acts a little funky sometimes?
            String promptResponse = promptScanner.nextLine();
            while (true) {
            if (promptResponse.equals("status")) {
                playerCharacter.checkStatus(playerCharacter);
            } else {
                break;
                }
            promptResponse = promptScanner.nextLine();
            }

            int response = Integer.parseInt(promptResponse) - 1;
            Room nextRoom = currentRoom.exits.get(response);
            currentRoom.exits.clear();
            currentRoom = nextRoom;
        }
    }

    public static Room roomRandomizer(ArrayList<Room> rooms) {
        return rooms.get(new Random().nextInt(rooms.size()));
        //room should be removed from list
    }
}