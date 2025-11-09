package main.requests;

import io.javalin.Javalin;
import main.App;
import main.Game;
import main.room.EnemyRoom;
import main.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Rooms {
    public static void requestHandler() {
        Javalin server = App.INSTANCE.getServer();

        server.post("/rooms/change", ctx -> {
            record RoomIndex(int id) {}
            Room returnRoom = Game.roomChangeHandler(ctx.bodyAsClass(RoomIndex.class).id());
            ctx.json(returnRoom);
        });
        server.post("/rooms/getEnemies", ctx -> {
            record RoomId(int id) {}
            int id = ctx.bodyAsClass(RoomId.class).id();
            EnemyRoom room = (EnemyRoom) getRoom(id);
            ctx.json(room.getEnemies());
        });
        server.post("/rooms/getExits", ctx -> {
            record RoomId(int id) {}
            int id = ctx.bodyAsClass(RoomId.class).id();
            ArrayList<Room> roomExits = getRoom(id).getExits();
            ctx.json(roomExits);
        });
    }

    public static Room getRoom(int id) {
        return App.INSTANCE.getRooms()
                .stream()
                .filter(room -> room.getId() == id)
                .toList()
                .getFirst();
    }
}
