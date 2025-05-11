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
    }
}
