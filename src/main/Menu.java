package main;
import java.util.Scanner;

public class Menu {

    public static void inventoryLoop(Player player) {
        while (true) {
            boolean inventoryIsEmpty = player.checkInventory(false);

            if (inventoryIsEmpty) {
                return;
            }

            System.out.println("Would you like to use an item?\nInput the appropriate number to use it, or exit to return to gameplay.");
            player.printStatusLine();
            System.out.print(" Inventory> ");
            int response = responseHandler( 1, player.getInventory().size());

            if (response == -1) {
                System.out.println("You exit your inventory.");
                return;
            }

            player.getInventory().get(response - 1).getFirst().useItem(player);
        }
    }
    public static void relicLoop(Player player) {
        while (true) {
            boolean relicPouchIsEmpty = player.checkRelics(false);
            if (relicPouchIsEmpty) {
                return;
            }

            System.out.println("Would you like to unequip a relic?\nInput the appropriate number to do so, or exit to return to gameplay.");
            player.printStatusLine();
            System.out.print(" Relics> ");
            int response = responseHandler(1, player.getEquippedRelics().size());

            if (response == -1) {
                System.out.println("You shut your relic pouch.");
                return;
            }

            player.getEquippedRelics().get(response - 1).useItem(player);
        }
    }

    public static int responseHandler(int lowerBound, int upperBound) {
        Scanner promptScanner = new Scanner(System.in);
        while (true) {
            String promptResponse = promptScanner.nextLine();
            if (promptResponse.equals("exit")) {
                return -1;
            }
            int response;
            try {
                response = Integer.parseInt(promptResponse);
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
