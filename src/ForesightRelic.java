public class ForesightRelic extends Relic {
    public ForesightRelic() {
        this.name = "Relic of Foresight";
        this.description = "An interesting artifact that allows you to tell how many exits the next room has.";
    }

    @Override
    public void useRelic(Player player, Room room) {
        System.out.println("Ability: Relic of Foresight");
        for (int i = 0; i < room.numExits; i++) {
            System.out.println("Room " + (i+1) + " has " + room.exits.get(i).numExits + " exits.");
        }
    }
}
