package main.item.relic;

import main.Player;

public class RelicRelic extends Relic {
    public RelicRelic() {
        setName("Relic of Relics");
        setDescription("Er, somehow, equipping this relic lets you equip 2 more relics?");
    }

    @Override
    public void useItem(Player player) {
        if (this.equipped) {
            int relicsOverCapacity = player.getEquippedRelics().size() - (player.getRelicCap() - 3);
            if (relicsOverCapacity > 0) {
                System.out.println("You have too many relics equipped to remove this relic!");
                System.out.println("Unequip " + relicsOverCapacity + " relics to remove this relic.");
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
    }
}
