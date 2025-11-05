package main.requests;

import io.javalin.Javalin;
import main.App;
import main.item.relic.RelicID;
import main.room.Room;

import java.util.ArrayList;

public class Rooms {
    public static void requestHandler() {
        Javalin server = App.INSTANCE.getServer();

        server.get("/rooms/getPrintableRooms", ctx -> {
            ArrayList<String> exitStrings = new ArrayList<>();
            ArrayList<Room> exits = App.INSTANCE.getPlayer().getCurrentRoom().getExits();
            boolean hasForesight = App.INSTANCE.getPlayer().equippedRelicIndex(RelicID.FORESIGHT) != -1;
            for (Room room : exits) {
                exitStrings.add(room.getAppearance() + ((hasForesight) ? "(" + room.getNumExits() + " exits)" : ""));
            }
            ctx.json(exitStrings);
        });

//        server.get("/inventory/unequipRelic");
    }
}
