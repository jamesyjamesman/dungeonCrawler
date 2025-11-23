package main.item.relic;

import main.entity.Player;
import main.item.Item;
import main.item.ItemType;
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

    public void useRelic(Player player) {}

    @Override
    public String useItem(Player player) {
        if (this.isEquipped(player)) {
            if (player.unequipRelic(this)) {
                return "The " + this.getName() + " was unequipped!";
            }
            return "Your inventory is full!";
        } else {
            if (player.equipRelic(this)) {
                return "The " + this.getName() + " was equipped!";
            }
            return "Your relic pouch is full!";
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
        return player.equippedRelicIndex(this.relicID) != -1;
    }
    public RelicID getRelicType() {
        return this.relicID;
    }
}
