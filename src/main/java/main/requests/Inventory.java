package main.requests;

import io.javalin.Javalin;
import main.App;
import main.entity.Player;

public class Inventory {
    public static void requestHandler() {
        Javalin server = App.INSTANCE.getServer();

//        server.get("/inventory/unequipRelic");
    }
}
