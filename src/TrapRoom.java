public class TrapRoom extends Room {
    int damageDealt;
    public TrapRoom() {
        super();
        this.damageDealt = 5;
    }

    @Override
    public void doEvents(Player player) {
        super.doEvents(player);
        System.out.println("You took " + this.damageDealt + " damage!");
        player.currentHealth -= this.damageDealt;
    }
}
