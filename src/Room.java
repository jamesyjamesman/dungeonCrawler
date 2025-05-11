import java.util.ArrayList;

public class Room {
    String appearance;
    String description;
    int id;
    ArrayList<Room> exits;
    int numExits;
    public Room() {
        this.appearance = "";
        this.description = "";
        this.id = 0;
        this.exits = new ArrayList<>();
        this.numExits = 2;
    }

    public void doEvents(Player player) {
        System.out.println(this.description);
    }
}
