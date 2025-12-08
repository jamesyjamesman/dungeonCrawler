package main;

import main.entity.Player;
import main.item.relic.ForesightRelic;
import main.item.relic.RelicID;
import main.room.Room;
import main.room.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Game {

    public static Room roomChangeHandler(int roomID) {
        Player player = App.INSTANCE.getPlayer();
        ArrayList<Room> rooms = App.INSTANCE.getRooms();
        Room currentRoom = getRoomByID(rooms, roomID);
        player.setCurrentRoom(currentRoom);

        currentRoom.completeRoomActions(player);

        activateRooms(rooms, player);
        ArrayList<Room> activeRooms = new ArrayList<>(getRandomActiveRooms(rooms));
        createRoomExits(activeRooms, currentRoom);
        App.INSTANCE.setState(App.State.ROOM_END);
        return currentRoom;
    }

    public static Room getRoomByID(ArrayList<Room> rooms, int roomID) {
        for (Room room : rooms) {
            if (room.getId() == roomID) {
                return room;
            }
        }
        return null;
    }

    public static void createRoomExits(ArrayList<Room> activeRooms, Room room) {
        room.getExits().clear();

        for (int i = 0; i < room.getNumExits(); i++)
            room.addExit(getWeightedRoom(activeRooms));
    }

    public static void deactivateRelicRooms() {
        ArrayList<Room> rooms = App.INSTANCE.getRooms();
        for (Room checkRoom : rooms) {
            if (checkRoom.getType() == RoomType.RELIC) {
                checkRoom.setActive(false);
                }
            }
        }

    public static List<Room> getRandomActiveRooms(ArrayList<Room> rooms) {
        return rooms.stream()
                .filter(Room::getActive)
                .toList();
    }

    //could have off-by-one error
    public static Room getWeightedRoom(ArrayList<Room> activeRooms) {
        int totalWeight = 0;
        for (Room room : activeRooms) {
            totalWeight += room.getSelectionWeight();
        }
        int randomWeight = new Random().nextInt(totalWeight);
        for (Room room : activeRooms) {
            int weight = room.getSelectionWeight();
                randomWeight -= weight;
                if (randomWeight < 0) {
                    return room;
                }
        }
        System.out.println("This code should be inaccessible!");
        return null;
    }

    public static void activateRooms(ArrayList<Room> rooms, Player player) {
        rooms.stream()
                .filter(room -> !room.getActive())
                .filter(room -> room.getType() != RoomType.RELIC || !App.INSTANCE.getUnusedRelics().isEmpty())
                .forEach(room -> room.roomActivator(player));
    }
}

