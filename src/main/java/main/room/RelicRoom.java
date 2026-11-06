package main.room;

import main.App;
import main.Game;
import main.entity.Player;
import main.item.DummyItem;
import main.item.relic.Relic;

import java.util.ArrayList;
import java.util.Random;

public class RelicRoom extends ItemRoom {
    private final boolean hasCorpse;

    public RelicRoom(RelicRoomBuilder<?> builder) {
        super(builder);
        this.hasCorpse = builder.hasCorpse;
    }

    @Override
    public void completeRoomActions(Player player) {
        initializeRelic();
        super.completeRoomActions(player);
    }

    public void initializeRelic() {
        ArrayList<Relic> relicList = App.INSTANCE.getObtainableFindableRelics();
        if (relicList.isEmpty()) {
            Game.deactivateRelicRooms();
            this.setItem(new DummyItem());
            return;
        }
        Relic newRelic = relicList.get(new Random().nextInt(relicList.size()));
        // If not actually picked up, will be reactivated again shortly
        relicList.remove(newRelic);
        if (relicList.isEmpty())
            Game.deactivateRelicRooms();
        if (this.hasCorpse)
            newRelic.setCursed(new Random().nextInt(5) != 0);
        this.setItem(newRelic);
    }
}
