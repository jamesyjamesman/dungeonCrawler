package main.room;

import main.item.Item;
import main.item.relic.Relic;
import main.Main;
import main.Player;

public class ItemRoom extends Room {
    Item item;
    public ItemRoom() {
        super();
        this.item = null;
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);

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
        String response = Main.yesOrNo();
        if (response.equals("n")) {
            System.out.println("You chose to forgo the loot...");
            return;
        }
        player.itemPickup(this.item);
    }
    public void setItem(Item item) {
        this.item = item;
    }
}
