import java.util.ArrayList;

public class EnemyRoom extends Room {
    ArrayList<Enemy> enemies;
    String battleInitiationMessage;
    public EnemyRoom() {
        this.enemies = new ArrayList<>();
        this.battleInitiationMessage = "";
    }
}
