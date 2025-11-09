package main;

import io.javalin.Javalin;
import main.entity.Player;
import main.initialization.RelicInit;
import main.initialization.RoomInit;
import main.requests.RequestHandler;
import main.room.Room;
import main.App;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> config.staticFiles.add("/web"))
                .start(7070);

        App.INSTANCE.getInstance().setServer(app);
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

        Player player = new Player();
        app.setPlayer(player);

        app.setRooms(RoomInit.roomInit());
        Room startRoom = App.INSTANCE.getRooms().getFirst();

        player.setCurrentRoom(startRoom);

        app.setUnusedRelics(RelicInit.relicInit());
        Game.roomChangeHandler(startRoom.getId());
    }

    public static String pluralChecker(int numThings) {
        if (numThings == 1) {
            return "";
        } else {
            return "s";
        }
    }
}
