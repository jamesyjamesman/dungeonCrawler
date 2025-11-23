package main.requests;

import io.javalin.http.Context;
import main.App;
import main.Main;


public class GameRequests {

    @GetRequestHandler(endpoint = "/gameStart")
    public static void game(Context ctx) {
        Main.initializeApp();
        ctx.status(200);
        ctx.json(App.INSTANCE.getPlayer().getCurrentRoom().getExits());
    }
}