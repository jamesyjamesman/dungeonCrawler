package main;

import io.javalin.Javalin;
import io.javalin.http.Context;
import main.entity.Player;
import main.initialization.RelicInit;
import main.initialization.RoomInit;
import main.requests.PostRequestHandler;
import main.room.Room;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> config.staticFiles.add("/web"))
                .start(7070);

        App.INSTANCE.getInstance().setServer(app);

        //stops caching, which allows for "hot reloads"
        app.before("/**", ctx -> {
            ctx.header("Cache-Control", "no-store, no-cache, must-revalidate, proxy-revalidate");
            ctx.header("Pragma", "no-cache");
            ctx.header("Expires", "0"); // Or a date in the past
        });

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackages("main.requests")
                .addScanners(Scanners.MethodsAnnotated));

        for (Method method : reflections.getMethodsAnnotatedWith(PostRequestHandler.class)) {
            if (method.getParameterCount() != 1)
                throw new RuntimeException(method.getName() + " must have exactly one parameter to be a request handler.");
            if (method.getParameterTypes()[0] != Context.class)
                throw new RuntimeException("Request handler parameter must be type of Context, not '" + method.getParameterTypes()[0].getName() + "'");
            if (!Modifier.isStatic(method.getModifiers()))
                throw new RuntimeException(method.getName() + " must be static to be a request handler.");

            PostRequestHandler annotation = method.getAnnotation(PostRequestHandler.class);
            if (annotation.endpoint().isEmpty())
                throw new RuntimeException(method.getName() + " must have an endpoint");

            app.post(annotation.endpoint(), ctx -> method.invoke(null, ctx));
        }
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
