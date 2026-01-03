package main.requests;

import io.javalin.http.Context;
import main.App;
import main.Game;
import main.entity.Player;
import main.item.Item;
import main.room.*;

import java.util.ArrayList;
import java.util.UUID;

import static main.requests.GameRequests.setContextStatus;

public class Rooms {

    record RoomId(int id) {}
    record RoomChangeEvents(ArrayList<String> relicUseText, ArrayList<String> statusText, Room room, boolean playerAlive) {}
    record TrapInfo(int damageDealt, boolean playerDead) {}
    record RoomId_Shop(int roomID, UUID itemID) {}

    @PostRequestHandler(endpoint = "/rooms/change")
    public static void change(Context ctx) {
        Player player = App.INSTANCE.getPlayer();

        Room returnRoom = Game.roomChangeHandler(ctx.bodyAsClass(RoomId.class).id());

        RoomChangeEvents events = new RoomChangeEvents(player.useRelics(), player.statusHandler(false), returnRoom, player.getCurrentHealth() > 0);

        ctx.json(events);
    }

    @PostRequestHandler(endpoint = "/rooms/trapDamage")
    public static void trapDamage(Context ctx) {
        TrapRoom trapRoom = (TrapRoom) getRoomFromContext(ctx);
        Player player = App.INSTANCE.getPlayer();
        player.takeDamage(trapRoom.getDamageDealt());

        ctx.json(new TrapInfo(trapRoom.getDamageDealt(), player.isDead()));
    }

    @PostRequestHandler(endpoint = "/rooms/getEnemies")
    public static void getEnemies(Context ctx) {
        EnemyRoom room = (EnemyRoom) getRoomFromContext(ctx);
        ctx.json(room.getEnemies());
    }

    @PostRequestHandler(endpoint = "/rooms/itemPickup")
    public static void itemPickup(Context ctx) {
        ItemRoom room = (ItemRoom) getRoomFromContext(ctx);
        ctx.json(App.INSTANCE.getPlayer().itemPickup(room.getItem()));
    }

    @PostRequestHandler(endpoint = "/rooms/relicPickup")
    public static void relicPickup(Context ctx) {
        RelicRoom room = (RelicRoom) getRoomFromContext(ctx);
        ctx.json(App.INSTANCE.getPlayer().itemPickup(room.getRelic()));
    }

    @PostRequestHandler(endpoint = "/rooms/getExits")
    public static void getExits(Context ctx) {
        ArrayList<Room> roomExits = getRoomFromContext(ctx).getExits();
        ctx.json(roomExits);
    }

    @PostRequestHandler(endpoint = "/rooms/buyItem")
    public static void buyItem(Context ctx) {
        RoomId_Shop stuff = ctx.bodyAsClass(RoomId_Shop.class);
        int roomID = stuff.roomID();
        UUID itemUUID = stuff.itemID();
        ShopRoom room = (ShopRoom) getRoom(roomID);

        Item soldItem = room.getWares()
                .stream()
                .filter(item -> item.getUuid().equals(itemUUID))
                .findFirst().orElse(null);

        boolean success = room.sellItem(soldItem, App.INSTANCE.getPlayer());
        ctx.json(success);
    }

    @PostRequestHandler(endpoint = "/rooms/cleansePlayer")
    public static void cleansePlayer(Context ctx) {
        String output;
        Player player = App.INSTANCE.getPlayer();
        boolean fireRemoved = ((PureWaterRoom) player.getCurrentRoom()).selfFountainize();
        if (fireRemoved) {
            output = "You jumped in the fountain, and put out the fire on you!";
        } else {
            output = "...You're all wet now.";
        }
        ctx.json("\"" + output + "\"");
    }

    public static Room getRoom(int id) {
        return App.INSTANCE.getRooms()
                .stream()
                .filter(room -> room.getId() == id)
                .findFirst().orElse(null);
    }

    public static Room getRoomFromContext(Context ctx) {
        int id = ctx.bodyAsClass(RoomId.class).id();
        return getRoom(id);
    }
}
