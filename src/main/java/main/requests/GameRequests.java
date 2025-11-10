package main.requests;

import io.javalin.http.Context;
import main.App;
import io.javalin.Javalin;
import main.Game;
import main.Main;


public class GameRequests {

    @PostRequestHandler(endpoint = "/gameStart")
    public static void game(Context ctx) {
        Main.initializeApp();
        ctx.status(200);
        ctx.json(App.INSTANCE.getPlayer().getCurrentRoom().getExits());
    }
}