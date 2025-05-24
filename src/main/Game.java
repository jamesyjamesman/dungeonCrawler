package main;

import main.initialization.*;
import main.item.*;
import main.item.relic.ForesightRelic;
import main.item.relic.Relic;
import main.room.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {
    public static void gameLoop(Player playerCharacter, Room currentRoom, ArrayList<Room> rooms) {
        ArrayList<Relic> relicList = RelicInit.relicInit();
        //this shouldn't be here
        ArrayList<Item> itemList = ItemInit.itemInit();
        while (playerCharacter.getCurrentHealth() > 0) {

            Main.roomChecker(currentRoom, rooms, relicList, itemList);

            currentRoom.completeRoomActions(playerCharacter);

            activateRooms(rooms, playerCharacter);
            ArrayList<Room> activeRooms = new ArrayList<>(getRandomActiveRooms(rooms));
            String exitString = createExitsString(playerCharacter, activeRooms, currentRoom);

            System.out.println(exitString);
            playerCharacter.useRelics(currentRoom);

            int response = Main.responseHandler(playerCharacter, exitString, 1, currentRoom.getExits().size()) - 1;
            Room nextRoom = currentRoom.getExits().get(response);
            currentRoom.getExits().clear();
            currentRoom = nextRoom;


        }
        playerCharacter.doDeathSequence();
    }

    public static String createExitsString(Player player, ArrayList<Room> activeRooms, Room room) {
        String output = "";
        output = output.concat("Where would you like to go?\n");
        output = output.concat("You see " + room.getNumExits() + " exit" + Main.pluralChecker(room.getNumExits()) + ".\n");

        for (int i = 0; i < room.getNumExits(); i++) {
            room.addExit(getWeightedRoom(activeRooms));

            int foresightIndex = player.equippedRelicIndex("Relic of Foresight");

            output = output.concat((i + 1) + ". " + room.getExits().get(i).getAppearance());
            if (foresightIndex != -1) {
                ForesightRelic foresightRelic = (ForesightRelic) player.getEquippedRelics().get(foresightIndex);
                int numExits = foresightRelic.findNumExits(room, i);
                output = output.concat(" (" + numExits + " exit" + Main.pluralChecker(numExits) + ")\n");
            } else {
                output = output.concat("\n");
            }
        }
        return output;
    }

    public static void deactivateRelicRooms(ArrayList<Room> rooms) {
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
                .filter(room -> room.getType() != RoomType.RELIC)
                .forEach(room -> room.roomActivator(player));
    }
}

