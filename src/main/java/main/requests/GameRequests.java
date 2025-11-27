package main.requests;

import io.javalin.http.Context;
import main.App;
import main.Main;


public class GameRequests {

    @GetRequestHandler(endpoint = "/gameStart")
    public static void game(Context ctx) {
        Main.initializeApp();
        
        ctx.json(App.INSTANCE.getPlayer().getCurrentRoom().getExits());
    }

    public static void setContextStatus(Context ctx) {
        if (App.INSTANCE.getPlayer() == null || !App.INSTANCE.getPlayer().isDead()) {
            ctx.status(200);
        } else {
            ctx.status(403);
        }
    }
}