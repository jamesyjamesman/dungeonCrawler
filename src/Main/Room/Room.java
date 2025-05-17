package Main.Room;

import Main.Player;

import java.util.ArrayList;

public class Room {
    String appearance;
    String description;
    int id;
    ArrayList<Room> exits;
    int numExits;
    public Room() {
        this.appearance = "It looks like a completely normal room from here.";
        this.description = "It's completely empty.";
        this.id = 0;
        this.exits = new ArrayList<>();
        this.numExits = 2;
    }

    public void completeRoomActions(Player player) {
        System.out.println(this.description);
        player.incrementRoomsTraversed();
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void addExit(Room exit) {
        this.exits.add(exit);
    }
    public ArrayList<Room> getExits() {
        return this.exits;
    }
    public void setAppearance(String appearance) {
        this.appearance = appearance;
    }
    public String getAppearance() {
        return this.appearance;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setNumExits(int numExits) {
        this.numExits = numExits;
    }
    public int getNumExits() {
        return this.numExits;
    }

}
