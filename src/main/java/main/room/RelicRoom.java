package main.room;

import main.App;
import main.Game;
import main.entity.Player;
import main.item.relic.Relic;

import java.util.ArrayList;
import java.util.Random;

public class RelicRoom extends Room {
    private final boolean hasCorpse;
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
    }

    public void initializeRelic() {
        ArrayList<Relic> relicList = App.INSTANCE.getUnusedRelics();
        Relic newRelic = relicList.get(new Random().nextInt(relicList.size()));
//        added back when sold or if not picked up in Player.itemPickup().
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
