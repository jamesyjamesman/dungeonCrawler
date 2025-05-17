package Main.Room;

import Main.Item.Item;
import Main.Item.Relic;
import Main.Main;
import Main.Player;

import java.util.Scanner;

public class ItemRoom extends Room {
    Item item;
    public ItemRoom() {
        super();
        this.item = null;
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);

        Scanner lineScanner = new Scanner(System.in);

        //this should be defined on the object itself but because the item isn't defined initially it crashes, which is not great.
        if (this.id == 10) {
            System.out.println("Would you like to ...look through the corpse? (y/n)");
        } else {
            System.out.println("Would you like to take the " + this.item.getName() + "? (y/n)");
        }
        if (player.equippedRelicIndex("Relic of Curse Detection") > -1) {
            if (this.item instanceof Relic checkRelic && checkRelic.isCursed()) {
                    System.out.println("Warning! The " + this.item.getName() + " is cursed!");
            }
        }
        String response = Main.checkForCommands(player, lineScanner.nextLine());
        if (response.equals("no") || response.equals("n")) {
            System.out.println("You chose to forgo the loot...");
            return;
        }

        player.addItemToInventory(this.item);

        if (this.item instanceof Relic) {
            System.out.println("Would you like to equip the " + this.item.getName() + " now? (y/n)");
            response = Main.checkForCommands(player, lineScanner.nextLine());
            if (response.equals("yes") || response.equals("y")) {
                this.item.useItem(player);
                System.out.println("The " + this.item.getName() + " has been equipped!");
                return;
            }
        }
        System.out.println("You stash the " + this.item.getName() + " in your bag.");
    }
    public void setItem(Item item) {
        this.item = item;
    }
}
