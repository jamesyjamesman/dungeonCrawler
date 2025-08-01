package main;

import main.initialization.PlayerInit;
import main.initialization.RoomInit;
import main.room.Room;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        App app = App.INSTANCE.getInstance();
        JFrame frame = SwingRenderer.componentFactory();
        app.setFrame(frame);

        SwingRenderer.renderer(frame);

        Player playerCharacter = PlayerInit.playerInit();
        app.setPlayer(playerCharacter);

        SwingRenderer.createPopup("Welcome to the simulation!\nYou will be presented choices on where to proceed.\nPress the appropriate button or type your answer in the field in the bottom left.\nGood luck!\n");

        ArrayList<Room> rooms = RoomInit.roomInit();

        Room firstRoom = rooms.getFirst();

        Game.gameLoop(playerCharacter, firstRoom, rooms, frame);
    }

    public static int getIntegerResponse(Player player, int lowerBound, int upperBound) {
        String originalResponse = SwingRenderer.getTempText().toLowerCase();
        String newResponse;
        boolean once = false;
        while (true) {
            newResponse = SwingRenderer.getTempText().toLowerCase();
            if (newResponse.isEmpty()) {
                continue;
            }
            if (once && newResponse.equals(originalResponse)) {
                continue;
            }
            once = true;
            String promptResponse = checkForCommands(player, newResponse);
            int response;
            try {
                response = Integer.parseInt(promptResponse);
            } catch(NumberFormatException e) {
                SwingRenderer.changeLabelText("Invalid response!", ComponentType.LABEL_ERROR);
                continue;
            }
            if (response > upperBound || response < lowerBound) {
                SwingRenderer.changeLabelText("Out of bounds!", ComponentType.LABEL_ERROR);
                continue;
            }
            return response;
        }
    }

    public static boolean parseResponseAsBoolean() {
        //this could be done better
        SwingRenderer.changeAnswerVisibility(true);
        String originalResponse = SwingRenderer.getTempText();
        String newResponse;
        boolean once = false;
        while (true) {
            newResponse = SwingRenderer.getTempText();
            if (newResponse.isEmpty()) {
                continue;
            }
            if (once && newResponse.equals(originalResponse)) {
                continue;
            }
            once = true;
            if (newResponse.equals("y") || newResponse.equals("yes")) {
                SwingRenderer.changeAnswerVisibility(false);
                return true;
            } else if (newResponse.equals("n") || newResponse.equals("no")) {
                SwingRenderer.changeAnswerVisibility(false);
                return false;
            }
            SwingRenderer.changeLabelText("Invalid response!", ComponentType.LABEL_ERROR);
        }
    }

    //TODO: add okay button similar to yes/no
    public static void waitForResponse() {
        while (true) {
            String response = SwingRenderer.getTempText();
            if (response.isEmpty()) {
                continue;
            }
            return;
        }
    }

    public static String checkForCommands(Player player, String input) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            switch (input) {
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
