import java.util.ArrayList;

public class EnemyRoom extends Room {
    ArrayList<Enemy> enemies;
    String battleInitiationMessage;
    public EnemyRoom() {
        super();
        this.enemies = new ArrayList<>();
        this.battleInitiationMessage = "";
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        System.out.println(this.battleInitiationMessage);
        System.out.println("You took " + this.enemies.size() * 3 + " damage!");
        player.currentHealth -= this.enemies.size() * 3;
    }
}
