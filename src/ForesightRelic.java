public class ForesightRelic extends Relic {
    public ForesightRelic() {
        this.name = "Relic of Foresight";
        this.description = "An interesting artifact that allows you to tell how many exits the next room has.";
    }

    @Override
    public void useRelic(Player player, Room room) {

    }
    public int findNumExits(Room room, int index) {
        return room.exits.get(index).numExits;
    }
}
