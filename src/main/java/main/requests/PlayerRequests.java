package main.requests;

import io.javalin.http.Context;
import main.App;
import main.entity.Player;
import main.item.Equippable;
import main.item.Item;
import main.item.buff.BuffItem;
import main.item.health.HealthItem;
import main.item.relic.Relic;
import main.item.relic.RelicID;

import java.util.Collection;
import java.util.UUID;

public class PlayerRequests {

    record ItemID(UUID uuid) {}
    record relicID(UUID uuid) {}
    record relicEnum(RelicID id) {}
    record OutputWithErrorFlag(String output, boolean error) {}
    record ItemWithUseCase(Item item, ItemUseCase useCase) {}

    @PostRequestHandler(endpoint = "/player/relicEquipped")
    public static void relicEquipped(Context ctx) {
        RelicID id = ctx.bodyAsClass(relicEnum.class).id;
        ctx.json(App.INSTANCE.getPlayer().hasRelicEquipped(id));
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
        UUID itemUUID = ctx.bodyAsClass(ItemID.class).uuid();
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
        Item item = getInventoryItemFromUUID(ctx);
        ItemUseCase useCase = item.useItem(App.INSTANCE.getPlayer());
        if (useCase == ItemUseCase.NO_GENERIC_USE)
            throw new IllegalStateException("Item has no generic use!");
        // Refresh item, some useItem() methods mutate the item
        ctx.json(new ItemWithUseCase(item, useCase));
    }

    @PostRequestHandler(endpoint = "/player/cleanseItem")
    public static void cleanseItem(Context ctx) {
        ctx.json(getItemOrRelic(ctx).cleanseItem(App.INSTANCE.getPlayer()));
    }

    @PostRequestHandler(endpoint = "/player/dropItem")
    public static void dropItem(Context ctx) {
        Item item = getInventoryItemFromUUID(ctx);
        boolean success = App.INSTANCE.getPlayer().discardItem(item);
        ctx.json(success);
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

    @PostRequestHandler(endpoint = "/player/unequip")
    public static void unequip(Context ctx) {
        Item item = getItemOrRelic(ctx);
        Equippable equip = (Equippable) item;
        ItemUseCase useCase = equip.unequip(App.INSTANCE.getPlayer());
        ctx.json(new ItemWithUseCase(item, useCase));
    }

    @PostRequestHandler(endpoint = "/player/equip")
    public static void equip(Context ctx) {
        Item item = getInventoryItemFromUUID(ctx);
        Equippable equip = (Equippable) item;
        ItemUseCase useCase = equip.equip(App.INSTANCE.getPlayer());
        ctx.json(new ItemWithUseCase(item, useCase));
    }

    public static Item getItemOrRelic(Context ctx) {
        Item item = getInventoryItemFromUUID(ctx);
        Relic relic = getRelicFromRelics(ctx);
        if (item != null) return item;
        return relic;
    }

    public static Item getInventoryItemFromUUID(Context ctx) {
        UUID uuid = ctx.bodyAsClass(ItemID.class).uuid();
        return App.INSTANCE.getPlayer().getInventory()
                .stream()
                .flatMap(Collection::stream)
                .filter(item -> item.getUuid().equals(uuid))
                .findFirst().orElse(null);
    }

    public static Relic getRelicFromRelics(Context ctx) {
        UUID uuid = ctx.bodyAsClass(relicID.class).uuid();
        return App.INSTANCE.getPlayer().getEquippedRelics()
                .stream()
                .filter(relic -> relic.getUuid().equals(uuid))
                .findFirst().orElse(null);
    }
}
