package Main;

import Main.Initialization.*;
import Main.Item.*;
import Main.Room.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
        ArrayList<Relic> relicList = RelicInit.relicInit();
        //this shouldn't be here
        ArrayList<Item> itemList = ItemInit.itemInit();
        while (playerCharacter.getCurrentHealth() > 0) {

            roomChecker(currentRoom, rooms, relicList, itemList);

            currentRoom.completeRoomActions(playerCharacter);
            System.out.println("Where would you like to go?");
            System.out.println("You see " + currentRoom.getNumExits() + " exit" + pluralChecker(currentRoom.getNumExits()) + ".");

            for (int i = 0; i < currentRoom.getNumExits(); i++) {
                currentRoom.addExit(roomRandomizer(rooms));

                int foresightIndex = playerCharacter.equippedRelicIndex("Relic of Foresight");

                System.out.print((i + 1) + ". " + currentRoom.getExits().get(i).getAppearance());
                if (foresightIndex != -1) {
                    ForesightRelic foresightRelic = (ForesightRelic) playerCharacter.getEquippedRelics().get(foresightIndex);
                    int numExits = foresightRelic.findNumExits(currentRoom, i);
                    System.out.println(" (" + numExits + " exit" + pluralChecker(numExits) + ")");
                } else {
                    System.out.println();
                }
            }
            System.out.println();
            playerCharacter.useRelics(currentRoom);

            int response = responseHandler(playerCharacter, 1, currentRoom.getExits().size()) - 1;
            Room nextRoom = currentRoom.getExits().get(response);
            currentRoom.getExits().clear();
            currentRoom = nextRoom;


        }
        playerCharacter.doDeathSequence();
    }

    public static void roomChecker(Room currentRoom, ArrayList<Room> rooms, ArrayList<Relic> relicList, ArrayList<Item> itemList) {

        if (currentRoom.getId() == 9 || currentRoom.getId() == 10) {
            ItemRoom newRoom = (ItemRoom) currentRoom;
            Relic newRelic = relicList.get(new Random().nextInt(relicList.size()));
            //this does not allow players to ever obtain a relic if they deny taking it.
            relicList.remove(newRelic);
            if (relicList.isEmpty()) {
                for (int i = 0; i < rooms.size(); i++) {
                    Room checkRoom = rooms.get(i);
                    if (checkRoom.getId() == 9 || checkRoom.getId() == 10) {
                        rooms.remove(checkRoom);
                    }
                }
            }
            if (newRoom.getId() == 10) {
                newRelic.setCursed(true);
            }
            newRoom.setItem(newRelic);
        }
        if (currentRoom.getId() == 12) {
            ItemRoom newRoom = (ItemRoom) currentRoom;
            Item item = itemList.get(new Random().nextInt(itemList.size()));
            newRoom.setItem(item);
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
}