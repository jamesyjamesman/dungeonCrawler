package main.requests;

import io.javalin.http.Context;
import main.App;
import main.Game;
import main.entity.Player;
import main.item.Item;
import main.room.EnemyRoom;
import main.room.Room;
import main.room.ShopRoom;

import java.util.ArrayList;
import java.util.UUID;

import static main.requests.GameRequests.setContextStatus;

public class Rooms {

    @PostRequestHandler(endpoint = "/rooms/change")
    public static void change(Context ctx) {
        Player player = App.INSTANCE.getPlayer();
        record RoomIndex(int id) {}
        Room returnRoom = Game.roomChangeHandler(ctx.bodyAsClass(RoomIndex.class).id());
        //todo move itemroom completeroomactions itempickup to here? maybe? idkkkkk

        record RoomChangeEvents(ArrayList<String> relicUseText, ArrayList<String> statusText, Room room, boolean playerAlive) {}
        RoomChangeEvents events = new RoomChangeEvents(player.useRelics(), player.statusHandler(false), returnRoom, player.getCurrentHealth() > 0);

        ctx.json(events);
        setContextStatus(ctx);
    }

    @PostRequestHandler(endpoint = "/rooms/getEnemies")
    public static void getEnemies(Context ctx) {
        record RoomId(int id) {}
        int id = ctx.bodyAsClass(RoomId.class).id();
        EnemyRoom room = (EnemyRoom) getRoom(id);
        ctx.json(room.getEnemies());
        setContextStatus(ctx);
    }

    @PostRequestHandler(endpoint = "/rooms/resetEnemies")
    public static void resetEnemies(Context ctx) {
        record RoomId(int id) {}
        int id = ctx.bodyAsClass(RoomId.class).id();
        EnemyRoom room = (EnemyRoom) getRoom(id);
        room.resetRoom();
        ctx.json(true);
        setContextStatus(ctx);
    }

    @PostRequestHandler(endpoint = "/rooms/getExits")
    public static void getExits(Context ctx) {
        record RoomId(int id) {}
        int id = ctx.bodyAsClass(RoomId.class).id();
        ArrayList<Room> roomExits = getRoom(id).getExits();
        ctx.json(roomExits);
        setContextStatus(ctx);
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
        setContextStatus(ctx);
    }

    public static Room getRoom(int id) {
        return App.INSTANCE.getRooms()
                .stream()
                .filter(room -> room.getId() == id)
                .findFirst().orElse(null);
    }
}
