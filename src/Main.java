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
        ArrayList<Room> rooms = RoomInit.roomInit(playerCharacter);

        Room firstRoom = rooms.getFirst();

        gameLoop(playerCharacter, firstRoom, rooms);
    }

    public static void gameLoop(Player playerCharacter, Room currentRoom, ArrayList<Room> rooms) {
        ArrayList<Relic> relicList = RelicInit.relicInit();
        //this shouldn't be here
        ArrayList<Item> itemList = ItemInit.itemInit();
        while (playerCharacter.currentHealth > 0) {

            roomChecker(currentRoom, rooms, relicList, itemList);

            currentRoom.completeRoomActions(playerCharacter);
            System.out.println("Where would you like to go?");
            System.out.println("You see " + currentRoom.numExits + " exit" + pluralChecker(currentRoom.numExits) + ".");

            for (int i = 0; i < currentRoom.numExits; i++) {
                currentRoom.exits.add(roomRandomizer(rooms));

                int foresightIndex = playerCharacter.equippedRelicIndex("Relic of Foresight");

                System.out.print((i + 1) + ". " + currentRoom.exits.get(i).appearance);
                if (foresightIndex != -1) {
                    ForesightRelic foresightRelic = (ForesightRelic) playerCharacter.equippedRelics.get(foresightIndex);
                    int numExits = foresightRelic.findNumExits(currentRoom, i);
                    System.out.println(" (" + numExits + " exit" + pluralChecker(numExits) + ")");
                } else {
                    System.out.println();
                }
            }
            System.out.println();
            playerCharacter.useRelics(currentRoom);

            int response = responseHandler(playerCharacter, 1, currentRoom.exits.size()) - 1;
            Room nextRoom = currentRoom.exits.get(response);
            currentRoom.exits.clear();
            currentRoom = nextRoom;


        }
        doDeathSequence(playerCharacter);
    }

    public static void roomChecker(Room currentRoom, ArrayList<Room> rooms, ArrayList<Relic> relicList, ArrayList<Item> itemList) {

        if (currentRoom.id == 9 || currentRoom.id == 10) {
            ItemRoom newRoom = (ItemRoom) currentRoom;
            Relic newRelic = relicList.get(new Random().nextInt(relicList.size()));
            //this does not allow players to ever obtain a relic if they deny taking it.
            relicList.remove(newRelic);
            if (relicList.isEmpty()) {
                for (int i = 0; i < rooms.size(); i++) {
                    Room checkRoom = rooms.get(i);
                    if (checkRoom.id == 9 || checkRoom.id == 10) {
                        rooms.remove(checkRoom);
                    }
                }
            }
            if (newRoom.id == 10) {
                newRelic.cursed = true;
            }
            newRoom.changeItem(newRelic);
        }
        if (currentRoom.id == 12) {
            ItemRoom newRoom = (ItemRoom) currentRoom;
            Item item = itemList.get(new Random().nextInt(itemList.size()));
            newRoom.changeItem(item);
        }
    }
    //can still crash if exit is entered in some scenarios
    public static int responseHandler(Player player, int lowerBound, int upperBound) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            String promptResponse = checkForCommands(player, promptScanner.nextLine());
            if (promptResponse.equals("exit")) {
                return -1;
            }
            int response;
            try {
                response = Integer.parseInt(promptResponse);
            } catch(NumberFormatException e) {
                System.out.println("Invalid response!");
                continue;
            }
            if (response > upperBound || response < lowerBound) {
                System.out.println("Out of bounds!");
                continue;
            }
            return response;
        }
    }

    public static String checkForCommands(Player player, String input) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            switch (input) {
                case "status" -> player.checkStatus();
                case "help" -> commandList();
                case "inventory" -> player.checkInventory(false);
                case "relics" -> player.checkRelics(false);
                case "kill" -> player.takeDamage(1000);
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
        System.out.println("relics: shows a list of equipped relics.");
    }

    public static String pluralChecker(int numThings) {
        if (numThings == 1) {
            return "";
        } else {
            return "s";
        }
    }
// if player died from food, it will not show that item as being consumed
    public static void endStatistics(Player player) {
        System.out.println("Player statistics:");
        System.out.println("You died on room #" + player.roomsTraversed + ".");
        System.out.println("Maximum health: " + player.maxHealth);
        System.out.println("Inventory:");
        player.checkInventory(true);
        System.out.println("Relics:");
        player.checkRelics(true);
    }
}