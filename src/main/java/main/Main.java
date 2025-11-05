package main;

import io.javalin.Javalin;
import main.entity.Player;
import main.initialization.PlayerInit;
import main.initialization.RelicInit;
import main.initialization.RoomInit;
import main.requests.RequestHandler;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> config.staticFiles.add("/web"))
                .start(7070);

        App.INSTANCE.setServer(app);
        RequestHandler.handler();

        //stops caching, which allows for "hot reloads"
        app.before("/**", ctx -> {
            ctx.header("Cache-Control", "no-store, no-cache, must-revalidate, proxy-revalidate");
            ctx.header("Pragma", "no-cache");
            ctx.header("Expires", "0"); // Or a date in the past
        });
    }

    public static void initializeApp() {
        App app = App.INSTANCE.getInstance();

        Player player = PlayerInit.playerInit();
        app.setPlayer(player);

        app.setUnusedRelics(RelicInit.relicInit());
        app.setRooms(RoomInit.roomInit());
    }

    public static String pluralChecker(int numThings) {
        if (numThings == 1) {
            return "";
        } else {
            return "s";
        }
    }
}
