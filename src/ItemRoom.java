import java.util.Random;
import java.util.Scanner;

public class ItemRoom extends Room {
    String itemName;
    int healthRestored;
    int[] restorationRange;
    int maxHealthChange;
    public ItemRoom() {
        super();
        this.itemName = "";
        this.healthRestored = 0;
        this.restorationRange = new int[2];
        this.maxHealthChange = 0;
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);

        this.healthRestored = new Random().nextInt(this.restorationRange[0], this.restorationRange[1]);

        Scanner lineScanner = new Scanner(System.in);
        System.out.println("Would you like to take the " + itemName + "? (y/n)");
        String response = Main.commandListener(player, lineScanner.nextLine());
        if (response.equals("no") || response.equals("n")) {
            System.out.println("You chose to forgo the loot...");
            return;
        }

        //obviously this would have to change if the item is not always consumable
        if (this.healthRestored < 0) {
            System.out.println("Yuck! The " + itemName + " was terrible... You lost " + this.healthRestored * -1 + " health.");
            player.takeDamage(this.healthRestored * -1);
        } else {
            if (this.maxHealthChange != 0) {
                System.out.println("Wow! A surge of power courses through you... your maximum health has increased by " + this.maxHealthChange + "!");
                //this should probably be a method on the player class
                player.maxHealth += this.maxHealthChange;
                player.currentHealth += this.maxHealthChange;
            }
            player.heal(this.healthRestored);
            System.out.println("Yum! That " + itemName + " was great! You replenished " + this.healthRestored + " health.");
        }
    }
}
