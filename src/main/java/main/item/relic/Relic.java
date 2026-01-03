package main.item.relic;

import main.entity.Player;
import main.item.Item;
import main.item.ItemType;
import main.requests.ItemUseCase;
import main.room.PureWaterRoom;

import java.util.Random;

public abstract class Relic extends Item {
    private boolean cursed;
    private final RelicID relicID;
    public Relic(String name, String description, double dropChance, RelicID relicID) {
        super(name, description, 25, true, dropChance, 0, ItemType.RELIC, true);
        this.setCursed(new Random().nextInt(5) == 0);
        this.relicID = relicID;
    }

    public abstract String useRelic(Player player);

    @Override
    public ItemUseCase useItem(Player player) {
        if (this.isEquipped(player)) {
            return player.unequipRelic(this);
        } else {
            if (player.equipRelic(this))
                return ItemUseCase.EQUIPPED;
            return ItemUseCase.POUCH_FULL;
        }
    }

    @Override
    public boolean cleanseItem(Player player) {
        ((PureWaterRoom) player.getCurrentRoom()).setFountainUsed(true);
        if (isCursed()) {
            setCursed(false);
            if (isEquipped(player)) {
                player.getCurrentStatuses().addCursed(-1);
            }
            return true;
        }
        return false;
    }

    public boolean isCursed() {
        return this.cursed;
    }
    public boolean isFindable() {
        return switch(this.relicID) {
            case RelicID.CURSE_HEAL, RelicID.SLIME, RelicID.CURE -> false;
            default -> true;
        };
    }

    public void setCursed(boolean cursed) {
        this.cursed = cursed;
    }

    public boolean isEquipped(Player player) {
        return player.hasRelicEquipped(this.relicID);
    }
    public RelicID getRelicType() {
        return this.relicID;
    }
}
