package main;

import io.javalin.Javalin;
import main.entity.Player;
import main.initialization.PlayerInit;
import main.initialization.RelicInit;
import main.initialization.RoomInit;
import main.room.Room;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        var app = Javalin.create(config -> config.staticFiles.add("/web"))
                .start(7070);

        //stops caching, which allows for "hot reloads"
        app.before("/**", ctx -> {
            ctx.header("Cache-Control", "no-store, no-cache, must-revalidate, proxy-revalidate");
            ctx.header("Pragma", "no-cache");
            ctx.header("Expires", "0"); // Or a date in the past
        });

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
        int spaceIndex = input.lastIndexOf(" ");
        String truncatedString;
        int secondaryNumber;
        if (spaceIndex != -1) {
            secondaryNumber = Integer.parseInt(input.substring(spaceIndex + 1));
            truncatedString = input.substring(0, spaceIndex);
        } else {
            secondaryNumber = 1000;
            truncatedString = input;
        }
            switch (truncatedString) {
                    //debug commands
            case "/kill" -> player.takeDamage(secondaryNumber);
            case "/heal" -> player.heal(secondaryNumber);
            case "/absorb" -> player.addAbsorption(secondaryNumber);
            case "/travel" -> player.setRoomsTraversed(secondaryNumber);
            case "/rich" -> player.addGold(secondaryNumber);
            //doesn't really do anything helpful
            case "/auto" -> {
                Thread loopThread = new Thread(() -> {
                    while (true) {
                        SwingRenderer.setTempText("1");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                loopThread.start();
            }
            case "/goto" -> {
                Room newRoom;
                try {
                    newRoom = Room.getRoomByID(secondaryNumber);
                } catch (NullPointerException e) {
                    SwingRenderer.appendLabelText("Room not found!", true, ComponentType.LABEL_ERROR);
                    return input;
                }
                Room currentRoom = player.getCurrentRoom();
                currentRoom.getExits().set(0, newRoom);
                SwingRenderer.setTempText("1");
            }
            case "/experienced" -> {
                player.changeExperience(secondaryNumber);
                player.levelUp();
            }
            case "/bagofholding" -> {
                player.setRelicCap(secondaryNumber);
                player.setInventoryCap(secondaryNumber);
            }
            case "/godmode" -> {
                player.increaseDamage(secondaryNumber);
                player.addAbsorption(secondaryNumber);
            }
            case "/disease" -> {
                Statuses statuses = player.getCurrentStatuses();
                statuses.setWeakened(secondaryNumber);
                statuses.setCursed(secondaryNumber);
                statuses.setPoison(secondaryNumber);
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
