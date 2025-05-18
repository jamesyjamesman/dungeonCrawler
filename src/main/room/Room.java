package main.room;

import main.Player;

import java.util.ArrayList;

public class Room {
    String appearance;
    String description;
    int id;
    ArrayList<Room> exits;
    int numExits;
    boolean active;
    RoomType type;


    public Room() {
        this.appearance = "It looks like a completely normal room from here.";
        this.description = "It's completely empty.";
        this.id = 0;
        this.exits = new ArrayList<>();
        this.numExits = 2;
        this.active = true;
        this.type = RoomType.NORMAL;
    }

    public void completeRoomActions(Player player) {
        System.out.println(this.description);
        player.incrementRoomsTraversed();
    }

    public RoomType getType() {
        return type;
    }
    public void setType(RoomType type) {
        this.type = type;
    }
    public boolean getActive() {
        return this.active;
    }
    public void setActive(boolean active) {
        this.active = active;
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
