package main;

import main.initialization.PlayerInit;
import main.initialization.RoomInit;
import main.item.Item;
import main.item.relic.Relic;
import main.room.ItemRoom;
import main.room.Room;
import main.room.RoomType;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JFrame frame = SwingRenderer.componentFactory();
//        SwingRenderer.createIntroductionPopup(frame);
        SwingRenderer.createPopup(frame, "Welcome to the simulation!\nYou will be presented choices on where to proceed.\nPress the appropriate button or type your answer in the field in the bottom left.\nGood luck!\n");
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
    public static int getIntegerResponse(JFrame frame, Player player, int lowerBound, int upperBound) {
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
            String promptResponse = checkForCommands(frame, player, newResponse);
            int response;
            try {
                response = Integer.parseInt(promptResponse);
            } catch(NumberFormatException e) {
                SwingRenderer.changeLabelText(frame, "Invalid response!", ComponentType.LABEL_ERROR);
                continue;
            }
            if (response > upperBound || response < lowerBound) {
                SwingRenderer.changeLabelText(frame, "Out of bounds!", ComponentType.LABEL_ERROR);
                continue;
            }
            return response;
        }
    }

    public static boolean parseResponseAsBoolean(JFrame frame) {
        //this could be done better
        SwingRenderer.changeAnswerVisibility(frame, true);
        String originalResponse = SwingRenderer.getTempText(frame);
        String newResponse;
        boolean once = false;
        while (true) {
            newResponse = SwingRenderer.getTempText(frame);
            if (newResponse.isEmpty()) {
                continue;
            }
            if (once && newResponse.equals(originalResponse)) {
                continue;
            }
            once = true;
            if (newResponse.equals("y") || newResponse.equals("yes")) {
                SwingRenderer.changeAnswerVisibility(frame, false);
                return true;
            } else if (newResponse.equals("n") || newResponse.equals("no")) {
                SwingRenderer.changeAnswerVisibility(frame, false);
                return false;
            }
            SwingRenderer.changeLabelText(frame, "Invalid response!", ComponentType.LABEL_ERROR);
        }
    }

    //TODO: add okay button similar to yes/no
    public static void waitForResponse(JFrame frame) {
        while (true) {
            String response = SwingRenderer.getTempText(frame);
            if (response.isEmpty()) {
                continue;
            }
            return;
        }
    }

    public static String checkForCommands(JFrame frame, Player player, String input) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            switch (input) {
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

    public static String pluralChecker(int numThings) {
        if (numThings == 1) {
            return "";
        } else {
            return "s";
        }
    }
}
