package main.requests;

import io.javalin.http.Context;
import main.App;
import main.item.relic.RelicID;

public class PlayerRequests {

    @PostRequestHandler(endpoint = "/player/relicEquipped")
    public static void playerRequests(Context ctx) {
        record relicEnum(RelicID id) {}
        RelicID id = ctx.bodyAsClass(relicEnum.class).id;
        ctx.json(App.INSTANCE.getPlayer().equippedRelicIndex(id) != -1);
    }
}
