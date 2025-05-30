package main;

import main.initialization.*;
import main.item.*;
import main.item.relic.Relic;
import main.room.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        //make a popup
        System.out.println("Welcome to the simulation!");
        System.out.println("You will be presented choices on where you would like to proceed. Choose carefully!");
        System.out.println("You can type commands into the console at any time. Try 'help' to see a list of commands.");
        JFrame frame = SwingRenderer.renderer();

        Player playerCharacter = PlayerInit.playerInit(frame);
        ArrayList<Room> rooms = RoomInit.roomInit();

        Room firstRoom = rooms.getFirst();

        Game.gameLoop(playerCharacter, firstRoom, rooms, frame);
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
    public static int responseHandler(JFrame frame, Player player, String repeatString, int lowerBound, int upperBound) {
        String originalResponse = SwingRenderer.getTempText(frame).toLowerCase();
        String newResponse;
        boolean once = false;
        while (true) {
            newResponse = SwingRenderer.getTempText(frame).toLowerCase();
            if (newResponse.isEmpty()) {
                continue;
            }
            if (once && newResponse.equals(originalResponse)) {
                continue;
            }
            once = true;
            String promptResponse = checkForCommands(frame, player, repeatString, newResponse);
            int response;
            try {
                response = Integer.parseInt(promptResponse);
            } catch(NumberFormatException e) {
                SwingRenderer.changeLabelText(frame, "Invalid response!", LabelType.ERROR);
                continue;
            }
            if (response > upperBound || response < lowerBound) {
                SwingRenderer.changeLabelText(frame, "Out of bounds!", LabelType.ERROR);
                continue;
            }
            return response;
        }
    }

    public static String yesOrNo(JFrame frame) {
        //this could be done better
        SwingRenderer.changeAnswerVisibility(frame, true);
        String originalResponse = SwingRenderer.getAnswerText(frame);
        String newResponse;
        boolean once = false;
        while (true) {
            newResponse = SwingRenderer.getAnswerText(frame);
            if (newResponse.isEmpty()) {
                continue;
            }
            if (once && newResponse.equals(originalResponse)) {
                continue;
            }
            once = true;
            if (newResponse.equals("y") || newResponse.equals("yes")) {
                SwingRenderer.changeAnswerVisibility(frame, false);
                return "y";
            } else if (newResponse.equals("n") || newResponse.equals("no")) {
                SwingRenderer.changeAnswerVisibility(frame, false);
                return "n";
            }
            SwingRenderer.changeLabelText(frame, "Invalid response!", LabelType.ERROR);
        }
    }

    public static String checkForCommands(JFrame frame, Player player, String exitString, String input) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            switch (input) {
//                case "status" -> player.checkStatus(frame);
//                case "help" -> commandList();
//                case "inventory", "inv" -> {
//                    Menu.inventoryLoop(player, frame);
//                    System.out.println(exitString);
//                    player.printStatusLine();
//                }
//                case "relics" -> {
//                    Menu.relicLoop(frame, player);
//                    System.out.println(exitString);
//                    player.printStatusLine();
//                }
                //debug commands
                case "kill" -> player.takeDamage(frame, 1000000);
                case "godmode" -> {
                    player.increaseDamage(1000);
                    player.addAbsorption(100000);
                }
                default -> {
                    return input;
                }
            }
            input = promptScanner.nextLine();
        }
    }

    //needs refactor
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
