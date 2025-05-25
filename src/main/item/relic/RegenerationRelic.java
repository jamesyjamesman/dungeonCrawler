package main.item.relic;

import main.DialogueType;
import main.Main;
import main.Player;
import main.room.Room;

import java.util.Random;

public class RegenerationRelic extends Relic {
    public RegenerationRelic() {
        setName("Relic of Regeneration");
        setDescription("This mystical artifact will heal you on occasion.");
    }

    @Override
    public void useRelic(Player player, Room room) {
        if (new Random().nextInt(3) == 0) {
        int amountHealed = player.heal(new Random().nextInt(1,5));
        if (amountHealed != 0) {
            System.out.println(Main.colorString("Your Relic of Regeneration healed you for " + amountHealed + " health!", DialogueType.HEAL));
            }
        }
        super.useRelic(player, room);
    }
}
