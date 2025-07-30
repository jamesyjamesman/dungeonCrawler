package main.item.relic;

import main.Player;
import main.item.Item;
import main.room.Room;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.Random;

public abstract class Relic extends Item {
    boolean equipped;
    boolean cursed;
    RelicID relicID;
    public Relic(){
        this.setValue(25);
        this.equipped = false;
        this.cursed = new Random().nextInt(5) == 0;
        this.relicID = null;
        this.setShopWeight(0);
    }

    public void useRelic(JFrame frame, Player player, Room room) {}

    @Override
    public void useItem(JFrame frame, Player player) {
        if(this.equipped) {player.unequipRelic(frame, this);}
        else {player.equipRelic(frame, this);}
        SwingRenderer.UIUpdater(frame, player);
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
