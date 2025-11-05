package main.item.relic;

import main.entity.Player;

public class RelicRelic extends Relic {
    public RelicRelic() {
        this(1);
    }
    public RelicRelic(double dropChance) {
        super("Relic of Relics",
                "Er, somehow, equipping this relic lets you equip 2 more relics?",
                dropChance,
                RelicID.RELICS);
    }

    @Override
    public void useItem(Player player) {
        if (this.isEquipped(player)) {
            int relicsOverCapacity = player.getEquippedRelics().size() - (player.getRelicCap() - 3);
            if (relicsOverCapacity > 0) {
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
