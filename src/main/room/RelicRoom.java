package main.room;

import main.App;
import main.Game;
import main.Main;
import main.Player;
import main.item.relic.Relic;
import main.item.relic.RelicID;
import main.swing.SwingRenderer;

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
    public void completeRoomActions(Player player) {
        initializeRelic();
        super.completeRoomActions(player);

        if (this.hasCorpse) {
            SwingRenderer.appendMainLabelText("Would you like to ...look through the corpse? (y/n)\n", true);
        } else {
            SwingRenderer.appendMainLabelText("Would you like to take the " + this.relic.getName() + " from the pedestal? (y/n)", true);
        }

        if (player.equippedRelicIndex(RelicID.CURSE_DETECTION) > -1 && this.relic.isCursed()) {
            SwingRenderer.appendMainLabelText("Warning! The " + this.relic.getName() + " is cursed!", false);
        }

        boolean wantsItem = Main.parseResponseAsBoolean();
        if (wantsItem) {
            player.itemPickup(this.relic);
            player.checkInventory();
            return;
        }
        SwingRenderer.appendMainLabelText("You chose to forgo the loot...", false);
    }

    public void initializeRelic() {
        ArrayList<Relic> relicList = App.INSTANCE.getUnusedRelics();
        Relic newRelic = relicList.get(new Random().nextInt(relicList.size()));
//        this does not allow players to ever obtain a relic if they deny taking it.
        relicList.remove(newRelic);
        if (relicList.isEmpty()) {
                Game.deactivateRelicRooms();
        }
        if (this.hasCorpse) {
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
