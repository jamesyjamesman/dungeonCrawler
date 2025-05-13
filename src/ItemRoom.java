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
        String response = Main.inputHelper(player, lineScanner.nextLine());
        if (response.equals("no") || response.equals("n")) {
            System.out.println("You chose to forgo the loot...");
            return;
        }
        System.out.println("You stash the " + this.item.name + " in your bag.");
        player.addItemToInventory(this.item);
    }
}
