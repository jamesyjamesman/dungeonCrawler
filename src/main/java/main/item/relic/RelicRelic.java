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
    public String useItem(Player player) {
        if (this.isEquipped(player)) {
            int relicsOverCapacity = player.getEquippedRelics().size() - (player.getRelicCap() - 3);
            if (relicsOverCapacity > 0) {
                return "You have too many relics equipped to unequip this!";
            }
            if (player.unequipRelic(this)) {
                player.changeRelicCap(-3);
                return "The " + this.getName() + " was unequipped!";
            }
            return "Your inventory is full!";
        } else {
            if (player.equipRelic(this)) {
                player.changeRelicCap(3);
                return "The " + this.getName() + " was equipped!";
            }
            return "Your relic pouch is full!";
        }
    }
}
