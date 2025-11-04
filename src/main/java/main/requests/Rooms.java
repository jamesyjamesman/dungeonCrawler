package main.requests;

import io.javalin.Javalin;
import main.App;
import main.room.Room;

public class Rooms {
    public static void requestHandler() {
        Javalin server = App.INSTANCE.getServer();

        server.get("/rooms/getCurrent", ctx -> ctx.json(App.INSTANCE.getPlayer().getCurrentRoom()));

//        server.get("/inventory/unequipRelic");
    }
}
