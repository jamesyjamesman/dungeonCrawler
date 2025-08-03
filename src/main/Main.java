package main;

import main.initialization.PlayerInit;
import main.initialization.RelicInit;
import main.initialization.RoomInit;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        initializeApp();
        JFrame frame = App.INSTANCE.getFrame();

        SwingRenderer.renderer(frame);

        Game.gameLoop();
    }

    public static void initializeApp() {
        App app = App.INSTANCE.getInstance();
        JFrame frame = SwingRenderer.componentFactory();
        app.setFrame(frame);
        SwingRenderer.createPopup("Welcome to the simulation!\nYou will be presented choices on where to proceed.\nPress the appropriate button or type your answer in the field in the bottom left.\nGood luck!\n");

        Player player = PlayerInit.playerInit();
        app.setPlayer(player);

        app.setUnusedRelics(RelicInit.relicInit());
        app.setRooms(RoomInit.roomInit());
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
                SwingRenderer.appendLabelText("Invalid response!", true, ComponentType.LABEL_ERROR);
                continue;
            }
            if (response > upperBound || response < lowerBound) {
                SwingRenderer.appendLabelText("Out of bounds!", true, ComponentType.LABEL_ERROR);
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
            SwingRenderer.appendLabelText("Invalid response!", true, ComponentType.LABEL_ERROR);
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
        switch (input) {
            //debug commands
            case "/kill" -> player.takeDamage(1000000);
            case "/travel" -> player.setRoomsTraversed(1000);
            case "/godmode" -> {
                player.increaseDamage(1000);
                player.addAbsorption(100000);
            }
            case "/disease" -> {
                Statuses statuses = player.getCurrentStatuses();
                statuses.setWeakened(1000);
                statuses.setCursed(1000);
                statuses.setPoison(1000);
            }
        }
        SwingRenderer.UIUpdater(player);
        return input;
    }

    public static String pluralChecker(int numThings) {
        if (numThings == 1) {
            return "";
        } else {
            return "s";
        }
    }
}
