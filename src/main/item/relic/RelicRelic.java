package main.item.relic;

import main.Player;
import main.swing.SwingRenderer;

public class RelicRelic extends Relic {
    public RelicRelic() {
        setName("Relic of Relics");
        setType(RelicID.RELICS);
        setDescription("Er, somehow, equipping this relic lets you equip 2 more relics?");
    }

    @Override
    public void useItem(Player player) {
        if (this.isEquipped(player)) {
            int relicsOverCapacity = player.getEquippedRelics().size() - (player.getRelicCap() - 3);
            if (relicsOverCapacity > 0) {
                SwingRenderer.appendMainLabelText("You have too many relics equipped to remove this relic!\n" + "Unequip " + relicsOverCapacity + " relics to remove this relic.", false);
                SwingRenderer.UIUpdater(player);
                return;
            }
            if (player.unequipRelic(this)) {
                player.changeRelicCap(-3);
            }
        }
        else {
            if (player.equipRelic(this)) {
                player.changeRelicCap(3);
            }
        }
        SwingRenderer.UIUpdater(player);
    }
}
