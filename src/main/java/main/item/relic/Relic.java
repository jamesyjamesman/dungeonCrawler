package main.item.relic;

import main.entity.Player;
import main.item.Item;

import java.util.Random;

public abstract class Relic extends Item {
    private boolean cursed;
    private final RelicID relicID;
    public Relic(String name, String description, double dropChance, RelicID relicID) {
        super(name, description, 25, true, dropChance, 0);
        this.cursed = new Random().nextInt(5) == 0;
        this.relicID = relicID;
    }

    public void useRelic(Player player) {}

    @Override
    public void useItem(Player player) {
        if(this.isEquipped(player)) {player.unequipRelic(this);}
        else {player.equipRelic(this);}
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
    public RelicID getType() {
        return this.relicID;
    }
}
