package main.requests;

import io.javalin.Javalin;
import main.App;
import main.item.relic.RelicID;

public class PlayerRequests {
    public static void playerRequests() {
        Javalin server = App.INSTANCE.getServer();

        server.post("/player/relicEquipped", ctx -> {
            record relicEnum(RelicID id) {}
            RelicID id = ctx.bodyAsClass(relicEnum.class).id;
            System.out.println(App.INSTANCE.getPlayer().equippedRelicIndex(id) != -1);
            ctx.json(App.INSTANCE.getPlayer().equippedRelicIndex(id) != -1);
        });
    }
}
