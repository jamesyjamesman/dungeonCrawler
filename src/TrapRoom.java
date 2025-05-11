public class TrapRoom extends Room {
    int damageDealt;
    public TrapRoom() {
        super();
        this.damageDealt = 5;
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        System.out.println("You took " + this.damageDealt + " damage!");
        player.takeDamage(this.damageDealt);
    }
}
