package main.requests;

import io.javalin.http.Context;
import main.App;
import main.entity.Player;
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
        String output;
        boolean error = false;
        Item item = getInventoryItemFromUUID(ctx);
        switch (item.useItem(App.INSTANCE.getPlayer())) {
            case ItemUseCase.HEALTH -> output = "The " + item.getName() + " healed you for " + ((HealthItem) item).getHealthRestored() + " health!";
            case ItemUseCase.NO_HEALTH -> output = "You ate the " + item.getName() + ", but nothing happened.";
            case ItemUseCase.NEGATIVE_HEALTH -> output = "Yuck! That " + item.getName() + " was gross... You lost " + ((HealthItem) item).getHealthRestored() * -1 + " health!";
            case ItemUseCase.BUFF -> output = ((BuffItem) item).createOutputString();
            case ItemUseCase.EQUIPPED -> output = "The " + item.getName() + " was equipped!";
            case ItemUseCase.UNEQUIPPED -> output = "The " + item.getName() + " was unequipped!";
            case ItemUseCase.WEAPON_ALREADY_EQUIPPED -> {
                output = "The " + item.getName() + " could not be equipped, because the " +
                    App.INSTANCE.getPlayer().getEquippedWeapon().getName() + " is already equipped!";
                error = true;
            }
            case ItemUseCase.POUCH_FULL -> {
                output = "Your relic pouch is full!";
                error = true;
            }
            default -> throw new IllegalStateException();
        }

        //this is bad but oh well
        if (item instanceof HealthItem healthItem && healthItem.getAddedAbsorption() != 0) {
            output += " Also, the " + healthItem.getName() + " granted " + healthItem.getAddedAbsorption() + " points of absorption!";
        }
        ctx.json(new OutputWithErrorFlag(output, error));
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
        String output;
        boolean error = false;
        Relic relic = getRelicFromRelics(ctx);
        switch (relic.useItem(App.INSTANCE.getPlayer())) {
            case ItemUseCase.UNEQUIPPED -> output = "The " + relic.getName() + " was unequipped!";
            case ItemUseCase.INVENTORY_FULL -> {
                output = "Your inventory is full!";
                error = true;
            }
            case ItemUseCase.INVENTORY_OVERFLOW -> {
                output = "You have too many items to unequip your " + relic.getName() + "!";
                error = true;
            }
            case ItemUseCase.POUCH_OVERFLOW -> {
                output = "You have too many relics equipped to unequip your " + relic.getName() + "!";
                error = true;
            }
            case ItemUseCase.RELIC_CURSED -> {
                output = "You cannot unequip a cursed relic!";
                error = true;
            }
            default -> throw new IllegalStateException("Relics can only be unequipped from the relics menu!");
        }
        ctx.json(new OutputWithErrorFlag(output, error));
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
