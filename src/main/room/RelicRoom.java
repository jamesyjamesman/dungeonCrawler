package main.room;

import main.Game;
import main.Main;
import main.Player;
import main.initialization.RelicInit;
import main.item.Item;
import main.item.relic.Relic;
import main.item.relic.RelicID;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class RelicRoom extends Room {
    boolean hasCorpse;
    Relic relic;
    public RelicRoom(boolean hasCorpse) {
        this.hasCorpse = hasCorpse;
        this.relic = null;
    }

    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        initializeRelic();
        super.completeRoomActions(player, frame);

        if (this.hasCorpse) {
            SwingRenderer.appendMainLabelText(frame, "Would you like to ...look through the corpse? (y/n)\n", true);
        } else {
            SwingRenderer.appendMainLabelText(frame, "Would you like to take the " + this.relic.getName() + " from the pedestal? (y/n)", true);
        }

        if (player.equippedRelicIndex(RelicID.CURSE_DETECTION) > -1 && this.relic.isCursed()) {
            SwingRenderer.appendMainLabelText(frame, "Warning! The " + this.relic.getName() + " is cursed!", false);
        }

        boolean wantsItem = Main.parseResponseAsBoolean(frame);
        if (wantsItem) {
            player.itemPickup(frame, this.relic);
            player.checkInventory(frame);
            return;
        }
        SwingRenderer.appendMainLabelText(frame, "You chose to forgo the loot...", false);
    }

    //TODO: figure out how to stop repeat relics;
    public void initializeRelic() {
        ArrayList<Relic> relicList = RelicInit.relicInit();
        Relic newRelic = relicList.get(new Random().nextInt(relicList.size()));
        //this does not allow players to ever obtain a relic if they deny taking it.
//        relicList.remove(newRelic);
//        if (relicList.isEmpty()) {
//                Game.deactivateRelicRooms(rooms);
//        }
        if (this.getId() == 10) {
            newRelic.setCursed(new Random().nextInt(5) != 0);
        }
        this.setRelic(newRelic);
    }

    public Relic getRelic() {
        return this.relic;
    }
    public void setRelic(Relic relic) {
        this.relic = relic;
    }
}
