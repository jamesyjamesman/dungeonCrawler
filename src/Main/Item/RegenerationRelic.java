package Main.Item;

import Main.Player;
import Main.Room.Room;

import java.util.Random;

public class RegenerationRelic extends Relic {
    public RegenerationRelic() {
        this.name = "Relic of Regeneration";
        this.description = "This mystical artifact will heal you on occasion.";
    }

    @Override
    public void useRelic(Player player, Room room) {
        if (new Random().nextInt(4) == 0) {
        int amountHealed = player.heal(new Random().nextInt(1,4));
        if (amountHealed != 0) {
            System.out.println("Your Relic of Regeneration healed you for " + amountHealed + " health!");
            }
        }
        super.useRelic(player, room);
    }
}
