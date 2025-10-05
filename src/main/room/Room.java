package main.room;

import main.App;
import main.Player;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.ArrayList;

public class Room {
    private final String appearance;
    private final String description;
    private final String backgroundFileName;
    private final int id;
    //can be final?
    private ArrayList<Room> exits;
    private final int numExits;
    private boolean active;
    private final RoomType type;
    private final int roomsRequired;
    private final int selectionWeight;

    public Room(RoomBuilder builder) {
        this.appearance = builder.appearance != null ? builder.appearance :
                "It looks like a completely normal room from here.";
        this.description = builder.description != null ? builder.description :
                "It's completely empty.";
        this.backgroundFileName = builder.backgroundFileName != null ? builder.backgroundFileName :
                "default_background.png";
        this.id = builder.id;
        this.numExits = builder.numExits != 0 ? builder.numExits : 2;
        this.type = builder.type != null ? builder.type : RoomType.NORMAL;
        // default 0, int defaults to 0 so it works out
        this.roomsRequired = builder.roomsRequired;
        this.selectionWeight = builder.selectionWeight != 0 ? builder.selectionWeight : 10;

        this.active = true;
        this.exits = new ArrayList<>();
    }

    public static class RoomBuilder {
        private String appearance;
        private String description;
        private String backgroundFileName;
        private int id;
        private int numExits;
        private RoomType type;
        private int roomsRequired;
        private int selectionWeight;

        public RoomBuilder appearance(String appearance) {
            this.appearance = appearance;
            return this;
        }

        public RoomBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RoomBuilder backgroundFileName(String backgroundFileName) {
            this.backgroundFileName = backgroundFileName;
            return this;
        }

        public RoomBuilder id(int id) {
            this.id = id;
            return this;
        }

        public RoomBuilder numExits(int numExits) {
            this.numExits = numExits;
            return this;
        }

        public RoomBuilder type(RoomType type) {
            this.type = type;
            return this;
        }

        public RoomBuilder roomsRequired(int roomsRequired) {
            this.roomsRequired = roomsRequired;
            return this;
        }

        public RoomBuilder selectionWeight(int selectionWeight) {
            this.selectionWeight = selectionWeight;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }

    public void completeRoomActions(Player player) {
        SwingRenderer.changeBackgroundImage(this.backgroundFileName);
        SwingRenderer.appendLabelText(this.description + "\n", true, ComponentType.LABEL_DESCRIPTION);
        player.incrementRoomsTraversed();
        player.checkStatus();
        player.checkInventory();
        player.checkRelics();
    }

    public static Room getRoomByID(int id) {
        ArrayList<Room> rooms = App.INSTANCE.getRooms();
        for (Room room : rooms) {
            if (room.id == id) {
                return room;
            }
        }
        return null;
    }

    public RoomType getType() {
        return type;
    }
    public boolean getActive() {
        return this.active;
    }
    public void setActive(boolean active) {
        this.active = active;
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
    public String getAppearance() {
        return this.appearance;
    }
    public int getNumExits() {
        return this.numExits;
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

}
