package main.requests;

import io.javalin.http.Context;
import main.App;
import main.Game;
import main.room.EnemyRoom;
import main.room.Room;

import java.util.ArrayList;

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
        ctx.status(200);
    }

    @PostRequestHandler(endpoint = "/rooms/getExits")
    public static void getExits(Context ctx) {
        record RoomId(int id) {}
        int id = ctx.bodyAsClass(RoomId.class).id();
        ArrayList<Room> roomExits = getRoom(id).getExits();
        ctx.json(roomExits);
    }

    public static Room getRoom(int id) {
        return App.INSTANCE.getRooms()
                .stream()
                .filter(room -> room.getId() == id)
                .findFirst().orElse(null);
    }
}
