package main;

import main.initialization.*;
import main.item.*;
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

            if (currentRoom.getId() == 9001) {
                win(playerCharacter);
            }

            activateRooms(rooms, playerCharacter);
            ArrayList<Room> activeRooms = new ArrayList<>(getRandomActiveRooms(rooms));

            System.out.println("Where would you like to go?");
            System.out.println("You see " + currentRoom.getNumExits() + " exit" + Main.pluralChecker(currentRoom.getNumExits()) + ".");

            for (int i = 0; i < currentRoom.getNumExits(); i++) {
                currentRoom.addExit(getWeightedRoom(activeRooms));

                int foresightIndex = playerCharacter.equippedRelicIndex("Relic of Foresight");

                System.out.print((i + 1) + ". " + currentRoom.getExits().get(i).getAppearance());
                if (foresightIndex != -1) {
                    ForesightRelic foresightRelic = (ForesightRelic) playerCharacter.getEquippedRelics().get(foresightIndex);
                    int numExits = foresightRelic.findNumExits(currentRoom, i);
                    System.out.println(" (" + numExits + " exit" + Main.pluralChecker(numExits) + ")");
                } else {
                    System.out.println();
                }
            }
            System.out.println();
            playerCharacter.useRelics(currentRoom);

            int response = Main.responseHandler(playerCharacter, 1, currentRoom.getExits().size()) - 1;
            Room nextRoom = currentRoom.getExits().get(response);
            currentRoom.getExits().clear();
            currentRoom = nextRoom;


        }
        playerCharacter.doDeathSequence();
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
                .filter(room -> !room.getActive() ||
                        room.getType() != RoomType.RELIC)
                .forEach(room -> room.roomActivator(player));
    }

    public static void win(Player player) {
        System.out.println("You survived long enough to escape! You win!");
        player.endStatistics();
    }
}

