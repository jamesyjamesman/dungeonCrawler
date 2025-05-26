package main;

import main.initialization.*;
import main.item.*;
import main.item.relic.Relic;
import main.room.*;

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

        Game.gameLoop(playerCharacter, firstRoom, rooms);
    }
    //this needs a refactor
    //potential solution: create a relicRoom class that extends itemRoom, then make that portion of this a method on that.
    //otherwise, that portion could just be a method on itemRoom with the check as well.
    //add a boolean to itemRooms to indicate whether its item should be randomized or not, then make
    //a method to do so on the room itself.
    public static void roomChecker(Room currentRoom, ArrayList<Room> rooms, ArrayList<Relic> relicList, ArrayList<Item> itemList) {

        if (currentRoom.getType() == RoomType.RELIC) {
            ItemRoom newRoom = (ItemRoom) currentRoom;
            Relic newRelic = relicList.get(new Random().nextInt(relicList.size()));
            //this does not allow players to ever obtain a relic if they deny taking it.
            relicList.remove(newRelic);
            if (relicList.isEmpty()) {
                Game.deactivateRelicRooms(rooms);
            }
            if (newRoom.getId() == 10) {
                newRelic.setCursed(new Random().nextInt(5) != 0);
            }
            newRoom.setItem(newRelic);
        }
        if (currentRoom.getId() == 12) {
            ItemRoom newRoom = (ItemRoom) currentRoom;
            Item item = itemList.get(new Random().nextInt(itemList.size()));
            newRoom.setItem(item);
        }
    }
    public static int responseHandler(Player player, String repeatString, int lowerBound, int upperBound) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            System.out.print("❯ ");
            String promptResponse = checkForCommands(player, repeatString, promptScanner.nextLine().toLowerCase());
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
    public static String yesOrNo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("❯ ");
        while (true) {
            String response = scanner.nextLine().toLowerCase();
            if (response.equals("y") || response.equals("yes")) {
                return "y";
            } else if (response.equals("n") || response.equals("no")) {
                return "n";
            }
            System.out.println("Invalid response!");
            System.out.print("❯ ");
        }

    }

    public static String checkForCommands(Player player, String exitString, String input) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            switch (input) {
                case "status" -> player.checkStatus();
                case "help" -> commandList();
                case "inventory", "inv" -> {
                    Menu.inventoryLoop(player);
                    System.out.println(exitString);
                    player.printStatusLine();
                }
                case "relics" -> {
                    Menu.relicLoop(player);
                    System.out.println(exitString);
                    player.printStatusLine();
                }
                //debug commands
                case "kill" -> player.takeDamage(1000000);
                case "godmode" -> {
                    player.increaseDamage(1000);
                    player.addAbsorption(100000);
                }
                default -> {
                    return input;
                }
            }
            System.out.print("❯ ");
            input = promptScanner.nextLine();
        }
    }

    public static void commandList() {
        System.out.println("List of commands:");
        System.out.println("help: checks this command.");
        System.out.println("status: checks player's status and statistics.");
        System.out.println("inventory (inv): shows the contents of the player's inventory.");
        System.out.println("relics: shows a list of equipped relics.");
    }

    public static String pluralChecker(int numThings) {
        if (numThings == 1) {
            return "";
        } else {
            return "s";
        }
    }

    public static String colorString(String input, DialogueType type) {
        String output = switch (type) {
            case INVENTORY -> "\u001B[36m" + input;
            case LEVEL -> "\u001B[33m" + input;
            case BATTLE -> "\u001B[35m" + input;
            case NAVIGATION -> "\u001B[34m" + input;
            case DAMAGE -> "\u001B[31m" + input;
            case HEAL -> "\u001B[32m" + input;
        };
        return output + "\u001B[0m";
    }
}
