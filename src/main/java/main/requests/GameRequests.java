package main.requests;

import main.App;
import io.javalin.Javalin;
import main.Game;
import main.Main;

public class GameRequests {
    public static void game() {
        Javalin server = App.INSTANCE.getServer();
        server.post("/gameStart", ctx -> {
            Main.initializeApp();
            ctx.status(200);
            ctx.json(App.INSTANCE.getPlayer().getCurrentRoom().getExits());
        });
    }
}
