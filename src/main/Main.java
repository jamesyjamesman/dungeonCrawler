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
        JFrame frame = SwingRenderer.componentFactory();
        SwingRenderer.createPopup(frame, "Welcome to the simulation!\nYou will be presented choices on where to proceed.\nPress the appropriate button or type your answer in the field in the bottom left.\nGood luck!\n");
        Player playerCharacter = PlayerInit.playerInit(frame);
        ArrayList<Room> rooms = RoomInit.roomInit();

        Room firstRoom = rooms.getFirst();

        Game.gameLoop(playerCharacter, firstRoom, rooms, frame);
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
