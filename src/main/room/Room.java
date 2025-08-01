package main.room;

import main.Player;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.ArrayList;

public class Room {
    String appearance;
    String description;
    String backgroundFileName;
    int id;
    ArrayList<Room> exits;
    int numExits;
    boolean active;
    RoomType type;
    int roomsRequired;
    int selectionWeight;

    public Room() {
        this.appearance = "It looks like a completely normal room from here.";
        this.description = "It's completely empty.";
        this.backgroundFileName = "default_background.png";
        this.id = 0;
        this.exits = new ArrayList<>();
        this.numExits = 2;
        this.active = true;
        this.type = RoomType.NORMAL;
        this.roomsRequired = 0;
        this.selectionWeight = 10;
    }

    public void completeRoomActions(Player player) {
        SwingRenderer.changeBackgroundImage(this.backgroundFileName);
        SwingRenderer.changeLabelText(this.description, ComponentType.LABEL_DESCRIPTION);
        player.incrementRoomsTraversed();
        player.checkStatus();
        player.checkInventory();
        player.checkRelics();
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
    public void setRoomsRequired(int roomsRequired) {
        this.roomsRequired = roomsRequired;
    }
    public int getRoomsRequired() {
        return roomsRequired;
    }
    public void roomActivator(Player player) {
        if (player.getRoomsTraversed() >= getRoomsRequired()) {
            this.active = true;
        }
    }
    public int getSelectionWeight() {
        return selectionWeight;
    }
    public void setSelectionWeight(int selectionWeight) {
        this.selectionWeight = selectionWeight;
    }
    public void setBackgroundFileName(String backgroundFileName) {
        this.backgroundFileName = backgroundFileName;
    }

}
