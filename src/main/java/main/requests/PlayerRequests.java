package main.requests;

import io.javalin.http.Context;
import main.App;
import main.item.relic.RelicID;

import java.util.UUID;

public class PlayerRequests {

    @PostRequestHandler(endpoint = "/player/relicEquipped")
    public static void playerRequests(Context ctx) {
        record relicEnum(RelicID id) {}
        RelicID id = ctx.bodyAsClass(relicEnum.class).id;
        ctx.json(App.INSTANCE.getPlayer().equippedRelicIndex(id) != -1);
    }

    @PostRequestHandler(endpoint = "/player/getPlayer")
    public static void getPlayer(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer());
    }

    @PostRequestHandler(endpoint = "/player/getInventorySize")
    public static void getInventorySize(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer().calculateInventorySize());
    }

    @PostRequestHandler(endpoint = "/player/getTotalDamage")
    public static void getTotalDamage(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer().calculateWeakenedAttack());
    }

    //todo 100% absolutely should be a GET request
    @PostRequestHandler(endpoint = "/player/getInventory")
    public static void getInventory(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer().getInventory());
    }

    @PostRequestHandler(endpoint = "/player/getRelics")
    public static void getRelics(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer().getEquippedRelics());
    }

    @PostRequestHandler(endpoint = "/player/useInventoryItem")
    public static void useItem(Context ctx) {
        record itemID(UUID uuid) {}
        UUID uuid = ctx.bodyAsClass(itemID.class).uuid();
        App.INSTANCE.getPlayer().getInventory()
                .stream()
                .filter(itemGroup -> itemGroup.stream().anyMatch(item -> item.getUuid().equals(uuid)))
                .forEach(itemGroup -> itemGroup.getFirst().useItem(App.INSTANCE.getPlayer()));
        //todo return item used string
        ctx.json(true);
    }

    @PostRequestHandler(endpoint = "/player/unequipRelic")
    public static void unequipRelic(Context ctx) {
        record relicID(UUID uuid) {}
        UUID uuid = ctx.bodyAsClass(relicID.class).uuid();
        App.INSTANCE.getPlayer().getEquippedRelics()
                .stream()
                .filter(relic -> relic.getUuid().equals(uuid))
                .findFirst().orElse(null).useItem(App.INSTANCE.getPlayer());
        ctx.json(true);
    }
}
