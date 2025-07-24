package main.item.relic;

import main.Player;
import main.item.Item;
import main.room.Room;

import javax.swing.*;
import java.util.Random;

public abstract class Relic extends Item {
    boolean equipped;
    boolean cursed;
    RelicType relicType;
    public Relic(){
        this.equipped = false;
        this.cursed = new Random().nextInt(5) == 0;
        this.relicType = null;
    }

    public void useRelic(JFrame frame, Player player, Room room) {}

    @Override
    public void useItem(JFrame frame, Player player) {
        if(this.equipped) {player.unequipRelic(frame, this);}
        else {player.equipRelic(frame, this);}
        UIUpdater(frame, player);
    }

    public boolean isCursed() {
        return this.cursed;
    }

    public void setCursed(boolean cursed) {
        this.cursed = cursed;
    }
    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
    //instead of having a boolean on the object itself, this method could just check players relic bag
    public boolean isEquipped() {
        return this.equipped;
    }
    public RelicType getType() {
        return this.relicType;
    }
    public void setType(RelicType relicType) {
        this.relicType = relicType;
    }
}
