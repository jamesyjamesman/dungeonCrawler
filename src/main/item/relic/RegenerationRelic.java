package main.item.relic;

import main.Player;
import main.room.Room;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.Random;

public class RegenerationRelic extends Relic {
    public RegenerationRelic() {
        setName("Relic of Regeneration");
        setType(RelicID.REGENERATION);
        setDescription("This mystical artifact will heal you on occasion.");
    }

    @Override
    public void useRelic(JFrame frame, Player player, Room room) {
        if (new Random().nextInt(3) == 0) {
        int amountHealed = player.heal(new Random().nextInt(1,5));
        if (amountHealed != 0) {
            SwingRenderer.addHealthText("Your Relic of Regeneration healed you for " + amountHealed + " health!");
            }
        }
    }
}
