package main.item.relic;

import main.Player;
import main.item.Item;
import main.room.PureWaterRoom;
import main.room.Room;
import main.swing.LabelType;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.Random;

public abstract class Relic extends Item {
    boolean equipped;
    boolean cursed;
    public Relic(){
        this.equipped = false;
        this.cursed = new Random().nextInt(5) == 0;
    }

    //TODO
    //should be abstract, any calls should be removed
    public void useRelic(JFrame frame, Player player, Room room) {
    }

    public void cleanseRelic(JFrame frame, Player player) {
        if (this.isCursed()) {
            this.setCursed(false);
            SwingRenderer.changeLabelText(frame, "The " + this.getName() + " was cured!", LabelType.ERROR);
            player.getCurrentStatuses().setCursed(player.getCurrentStatuses().getCursed() - 1);
        } else {
            SwingRenderer.changeLabelText(frame, "That relic wasn't cursed...", LabelType.ERROR);
        }
        ((PureWaterRoom) player.getCurrentRoom()).setFountainUsed(true);
        player.checkRelics(frame);
        SwingRenderer.appendMainLabelText(frame, "The fountain ran dry!", false);
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        if(this.equipped) {player.unequipRelic(frame, this);}
        else {player.equipRelic(frame, this);}
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
}
