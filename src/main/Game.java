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
            System.out.println("Where would you like to go?");
            System.out.println("You see " + currentRoom.getNumExits() + " exit" + Main.pluralChecker(currentRoom.getNumExits()) + ".");

            for (int i = 0; i < currentRoom.getNumExits(); i++) {
                currentRoom.addExit(getRandomActiveRoom(rooms));

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

    public static Room getRandomActiveRoom(ArrayList<Room> rooms) {
        List<Room> activeRooms = rooms.stream()
                .filter(Room::getActive)
                .toList();
        return activeRooms.get(new Random().nextInt(activeRooms.size()));
    }

    public static void activateRooms(ArrayList<Room> rooms, Player player) {
        rooms.stream()
                .filter(room -> !room.getActive())
                .forEach(room -> room.roomActivator(player));
    }

    public static void win(Player player) {
        System.out.println("You survived long enough to escape! You win!");
        player.endStatistics();
    }
}

