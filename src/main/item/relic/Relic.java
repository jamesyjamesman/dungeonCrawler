package main.item.relic;

import main.entity.Player;
import main.item.Item;
import main.room.Room;
import main.swing.SwingRenderer;

import java.util.Random;

public abstract class Relic extends Item {
    boolean cursed;
    RelicID relicID;
    public Relic(String name, String description, double dropChance, RelicID relicID) {
        super(name, description, 25, true, dropChance, 0);
        this.cursed = new Random().nextInt(5) == 0;
        this.relicID = null;
    }

    public void useRelic(Player player, Room room) {}

    @Override
    public void useItem(Player player) {
        if(this.isEquipped(player)) {player.unequipRelic(this);}
        else {player.equipRelic(this);}
        SwingRenderer.UIUpdater(player);
    }

    public boolean isCursed() {
        return this.cursed;
    }

    public void setCursed(boolean cursed) {
        this.cursed = cursed;
    }
    //instead of having a boolean on the object itself, this method could just check players relic bag
    public boolean isEquipped(Player player) {
        return player.equippedRelicIndex(this.relicID) != -1;
    }
    public RelicID getType() {
        return this.relicID;
    }
    public void setType(RelicID relicID) {
        this.relicID = relicID;
    }
}
