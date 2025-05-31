package main.item.relic;

import main.Player;
import main.swing.SwingRenderer;

import javax.swing.*;

public class RelicRelic extends Relic {
    public RelicRelic() {
        setName("Relic of Relics");
        setDescription("Er, somehow, equipping this relic lets you equip 2 more relics?");
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        if (this.equipped) {
            int relicsOverCapacity = player.getEquippedRelics().size() - (player.getRelicCap() - 3);
            if (relicsOverCapacity > 0) {
                SwingRenderer.appendMainLabelText(frame, "You have too many relics equipped to remove this relic!\n" + "Unequip " + relicsOverCapacity + " relics to remove this relic.");
                return;
            }
            if (player.unequipRelic(frame, this)) {
                player.changeRelicCap(-3);
            }
        }
        else {
            if (player.equipRelic(frame, this)) {
                player.changeRelicCap(3);
            }
        }
    }
}
