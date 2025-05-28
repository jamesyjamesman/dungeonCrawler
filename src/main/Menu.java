package main;
import javax.swing.*;
import java.util.Scanner;

public class Menu {

    public static void inventoryLoop(Player player, JFrame frame) {
        while (true) {
            boolean inventoryIsEmpty = player.checkInventory(frame, false);

            if (inventoryIsEmpty) {
                return;
            }

            System.out.println("Would you like to use an item?\nInput the appropriate number to use it, or exit to return to gameplay.");
            player.printStatusLine();
            System.out.print(" Inventory> ");
            int response = responseHandler(frame, 1, player.getInventory().size());

            if (response == -1) {
                System.out.println("You exit your inventory.");
                return;
            }

            player.getInventory().get(response - 1).getFirst().useItem(player);
        }
    }
    public static void relicLoop(JFrame frame, Player player) {
        while (true) {
            boolean relicPouchIsEmpty = player.checkRelics(frame, false);
            if (relicPouchIsEmpty) {
                return;
            }

            System.out.println("Would you like to unequip a relic?\nInput the appropriate number to do so, or exit to return to gameplay.");
            player.printStatusLine();
            System.out.print(" Relics> ");
            int response = responseHandler(frame, 1, player.getEquippedRelics().size());

            if (response == -1) {
                System.out.println("You shut your relic pouch.");
                return;
            }

            player.getEquippedRelics().get(response - 1).useItem(player);
        }
    }

    public static int responseHandler(JFrame frame, int lowerBound, int upperBound) {
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
            if (newResponse.equals("exit")) {
                return -1;
            }
            int response;
            try {
                response = Integer.parseInt(newResponse);
            } catch (NumberFormatException e) {
                System.out.println("Invalid response!");
                System.out.print("> ");
                continue;
            }
            if (response > upperBound || response < lowerBound) {
                System.out.println("Out of bounds!");
                System.out.print("> ");
                continue;
            }
            return response;
        }
    }
}
