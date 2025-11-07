package main.requests;

import io.javalin.Javalin;
import main.App;
import main.Game;
import main.room.Room;

import java.util.UUID;

public class Rooms {
    public static void requestHandler() {
        Javalin server = App.INSTANCE.getServer();

        server.post("/rooms/change", ctx -> {
            record RoomIndex(UUID uuid) {}
            Game.roomChangeHandler(ctx.bodyAsClass(RoomIndex.class).uuid());
            Room returnRoom = App.INSTANCE.getPlayer().getCurrentRoom();
            ctx.json(returnRoom);
        });

//        server.get("/inventory/unequipRelic");
    }
}
