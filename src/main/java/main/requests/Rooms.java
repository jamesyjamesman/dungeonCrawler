package main.requests;

import io.javalin.http.Context;
import main.App;
import main.Game;
import main.item.Item;
import main.room.EnemyRoom;
import main.room.Room;
import main.room.ShopRoom;

import java.util.ArrayList;
import java.util.UUID;

public class Rooms {

    @PostRequestHandler(endpoint = "/rooms/change")
    public static void change(Context ctx) {
        record RoomIndex(int id) {}
        Room returnRoom = Game.roomChangeHandler(ctx.bodyAsClass(RoomIndex.class).id());
        ctx.json(returnRoom);
    }

    @PostRequestHandler(endpoint = "/rooms/getEnemies")
    public static void getEnemies(Context ctx) {
        record RoomId(int id) {}
        int id = ctx.bodyAsClass(RoomId.class).id();
        EnemyRoom room = (EnemyRoom) getRoom(id);
        ctx.json(room.getEnemies());
    }

    @PostRequestHandler(endpoint = "/rooms/resetEnemies")
    public static void resetEnemies(Context ctx) {
        record RoomId(int id) {}
        int id = ctx.bodyAsClass(RoomId.class).id();
        EnemyRoom room = (EnemyRoom) getRoom(id);
        room.resetRoom();
        ctx.json(true);
    }

    @PostRequestHandler(endpoint = "/rooms/getExits")
    public static void getExits(Context ctx) {
        record RoomId(int id) {}
        int id = ctx.bodyAsClass(RoomId.class).id();
        ArrayList<Room> roomExits = getRoom(id).getExits();
        ctx.json(roomExits);
    }

    @PostRequestHandler(endpoint = "/rooms/buyItem")
    public static void buyItem(Context ctx) {
        record RoomId(int roomID, UUID itemID) {}
        RoomId stuff = ctx.bodyAsClass(RoomId.class);
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

    public static Room getRoom(int id) {
        return App.INSTANCE.getRooms()
                .stream()
                .filter(room -> room.getId() == id)
                .findFirst().orElse(null);
    }
}
