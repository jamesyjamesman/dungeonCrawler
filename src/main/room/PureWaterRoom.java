package main.room;

import main.LabelType;
import main.Main;
import main.Player;
import main.SwingRenderer;
import main.item.Item;
import main.item.relic.Relic;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PureWaterRoom extends Room {
    public PureWaterRoom() {}

    //I wanted to make it so you could purify apples to buff them and have no chance of being rotten, but I don't know if there's an easy way to do that.
    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);
        SwingRenderer.changeLabelText(frame, "You sense this fountain has some purifying properties.\n" +
                "Would you like to cleanse a relic? (y/n)", LabelType.MAIN);
        String response = Main.yesOrNo(frame);
        if (response.equals("y")) {
            List<Relic> relics = player.getEquippedRelics().stream()
                    .filter(Relic::isCursed)
                    .toList();
            ArrayList<Relic> cursedRelics = new ArrayList<>(relics);
            if (!player.getInventory().isEmpty()) {
                for (int i = 0; i < player.getInventory().size(); i++) {
                    Item item = player.getInventory().get(i).getFirst();
                    if (item instanceof Relic relic && relic.isCursed()) {
                        cursedRelics.add(relic);
                    }
                }
            }
            if (cursedRelics.isEmpty()) {
                SwingRenderer.changeLabelText(frame, "You don't have any cursed relics!", LabelType.ERROR);
                return;
            }
            Relic selectedRelic = cursedRelics.get(new Random().nextInt(cursedRelics.size()));
            selectedRelic.setCursed(false);
            SwingRenderer.changeLabelText(frame, "Your " + selectedRelic.getName() + " has been cured!", LabelType.ERROR);
        }
    }
}
