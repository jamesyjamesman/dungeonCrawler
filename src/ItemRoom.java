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
        System.out.println("Would you like to take the " + this.item.name + "? (y/n)");
        if (player.equippedRelicIndex("Relic of Curse Detection") > -1) {
            if (this.item instanceof Relic checkRelic && checkRelic.isCursed()) {
                    System.out.println("Warning! The " + this.item.name + " is cursed!");
            }
        }
        String response = Main.inputHelper(player, lineScanner.nextLine());
        if (response.equals("no") || response.equals("n")) {
            System.out.println("You chose to forgo the loot...");
            return;
        }

        player.addItemToInventory(this.item);

        if (this.item instanceof Relic) {
            System.out.println("Would you like to equip this now? (y/n)");
            response = Main.inputHelper(player, lineScanner.nextLine());
            if (response.equals("yes") || response.equals("y")) {
                this.item.useItem(player);
                System.out.println("The " + this.item.name + " has been equipped!");
                return;
            }
        }
        System.out.println("You stash the " + this.item.name + " in your bag.");
    }
    public void changeItem(Item item) {
        this.item = item;
    }
}
