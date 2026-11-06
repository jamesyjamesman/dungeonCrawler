package main.item.relic;

import main.entity.Player;
import main.item.Item;
import main.requests.ItemUseCase;

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
    public ItemUseCase equip(Player player) {
        if (player.equipRelic(this)) {
            player.changeRelicCap(3);
            return ItemUseCase.EQUIPPED;
        }
        return ItemUseCase.POUCH_FULL;
    }

    @Override
    public ItemUseCase unequip(Player player) {
        int relicsOverCapacity = player.getEquippedRelics().size() - (player.getRelicCap() - 3);
        if (relicsOverCapacity > 0) {
            return ItemUseCase.POUCH_OVERFLOW;
        }
        ItemUseCase unequipStatus = player.unequipRelic(this);
        if (unequipStatus == ItemUseCase.UNEQUIPPED) {
            player.changeRelicCap(-3);
        }
        return unequipStatus;
    }
}
