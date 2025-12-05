package main.requests;

import io.javalin.http.Context;
import main.App;
import main.entity.Player;
import main.item.Item;
import main.item.relic.Relic;
import main.item.relic.RelicID;

import java.util.Collection;
import java.util.UUID;

import static main.requests.GameRequests.setContextStatus;

public class PlayerRequests {

    @PostRequestHandler(endpoint = "/player/relicEquipped")
    public static void playerRequests(Context ctx) {
        record relicEnum(RelicID id) {}
        RelicID id = ctx.bodyAsClass(relicEnum.class).id;
        ctx.json(App.INSTANCE.getPlayer().equippedRelicIndex(id) != -1);
        
    }

    @GetRequestHandler(endpoint = "/player/getPlayer")
    public static void getPlayer(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer());
        
    }

    @GetRequestHandler(endpoint = "/player/getInventorySize")
    public static void getInventorySize(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer().calculateInventorySize());
        
    }

    @GetRequestHandler(endpoint = "/player/getTotalDamage")
    public static void getTotalDamage(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer().calculateWeakenedAttack());
        
    }

    @PostRequestHandler(endpoint = "/player/itemEquipped")
    public static void itemEquipped(Context ctx) {
        record itemID(UUID uuid) {}
        UUID itemUUID = ctx.bodyAsClass(itemID.class).uuid();
        Player player = App.INSTANCE.getPlayer();
        boolean equipped =
                player.getEquippedRelics()
                        .stream()
                        .anyMatch(relic -> relic.getUuid().equals(itemUUID)) ||
                player.getEquippedWeapon().getUuid().equals(itemUUID);
        ctx.json(equipped);
        
    }

    @GetRequestHandler(endpoint = "/player/getInventory")
    public static void getInventory(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer().getInventory());
        
    }

    @GetRequestHandler(endpoint = "/player/getRelics")
    public static void getRelics(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer().getEquippedRelics());
        
    }

    @GetRequestHandler(endpoint = "/player/getCurrentRoom")
    public static void getCurrentRoom(Context ctx) {
        ctx.json(App.INSTANCE.getPlayer().getCurrentRoom());
        
    }

    @PostRequestHandler(endpoint = "/player/useInventoryItem")
    public static void useItem(Context ctx) {
        String output = getInventoryItemFromUUID(ctx).useItem(App.INSTANCE.getPlayer());
        ctx.json("\"" + output + "\"");
        
    }

    @PostRequestHandler(endpoint = "/player/cleanseItem")
    public static void cleanseItem(Context ctx) {
        boolean cleansed = getInventoryItemFromUUID(ctx).cleanseItem(App.INSTANCE.getPlayer());
        ctx.json(cleansed);
        
    }

    @PostRequestHandler(endpoint = "/player/dropItem")
    public static void dropItem(Context ctx) {
        Item item = getInventoryItemFromUUID(ctx);
        App.INSTANCE.getPlayer().discardItem(item);
        ctx.json(true);
        
    }

    @PostRequestHandler(endpoint = "/player/sellItem")
    public static void sellItem(Context ctx) {
        Item item = getInventoryItemFromUUID(ctx);
        boolean success = App.INSTANCE.getPlayer().sellItem(item);
        ctx.json(success);
        
    }

    //todo status if level up failed?
    @PostRequestHandler(endpoint = "/player/levelUp")
    public static void levelUp(Context ctx) {
        ctx.json("\"" + App.INSTANCE.getPlayer().levelUp().replaceAll("\n", "\\\\n") + "\"");
    }

    @PostRequestHandler(endpoint = "/player/cleanseRelic")
    public static void cleanseRelic(Context ctx) {
        boolean cleansed = getRelicFromRelics(ctx).cleanseItem(App.INSTANCE.getPlayer());
        ctx.json(cleansed);
        
    }

    @PostRequestHandler(endpoint = "/player/unequipRelic")
    public static void unequipRelic(Context ctx) {
        //todo return output
        getRelicFromRelics(ctx).useItem(App.INSTANCE.getPlayer());
        ctx.json(true);
        
    }

    public static Item getInventoryItemFromUUID(Context ctx) {
        record ItemID(UUID uuid) {}
        UUID uuid = ctx.bodyAsClass(ItemID.class).uuid();
        return App.INSTANCE.getPlayer().getInventory()
                .stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getUuid().equals(uuid))
                .findFirst().orElse(null);
    }

    public static Relic getRelicFromRelics(Context ctx) {
        record relicID(UUID uuid) {}
        UUID uuid = ctx.bodyAsClass(relicID.class).uuid();
        return App.INSTANCE.getPlayer().getEquippedRelics()
                .stream()
                .filter(relic -> relic.getUuid().equals(uuid))
                .findFirst().orElse(null);
    }
}
