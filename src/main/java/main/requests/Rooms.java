package main.requests;

import io.javalin.Javalin;
import main.App;
import main.Game;
import main.item.relic.RelicID;
import main.room.Room;

import java.util.ArrayList;
import java.util.UUID;

public class Rooms {
    public static void requestHandler() {
        Javalin server = App.INSTANCE.getServer();

        server.post("/rooms/change", ctx -> {
            record RoomIndex(UUID uuid) {}
            Game.roomChangeHandler(ctx.bodyAsClass(RoomIndex.class).uuid());
            ctx.json(App.INSTANCE.getPlayer().getCurrentRoom().getExits());
        });

//        server.get("/inventory/unequipRelic");
    }
}
