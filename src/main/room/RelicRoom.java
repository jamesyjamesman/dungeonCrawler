package main.room;

import main.App;
import main.Game;
import main.Player;
import main.item.relic.Relic;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.ArrayList;
import java.util.Random;

public class RelicRoom extends Room {
    private final boolean hasCorpse;
    //todo make relic final if possible
    private Relic relic;

    public RelicRoom(RelicRoomBuilder<?> builder) {
        super(builder);
        this.hasCorpse = builder.hasCorpse;
        this.relic = null;
    }

    @Override
    public void completeRoomActions(Player player) {
        initializeRelic();
        super.completeRoomActions(player);

        if (this.hasCorpse) {
            SwingRenderer.appendLabelText("Upon further inspection, you see something shiny in the pile of bones. Hesitating slightly, you grab it.\n", false, ComponentType.LABEL_DESCRIPTION);
        }

        player.itemPickup(this.relic);
        SwingRenderer.UIUpdater(player);
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
