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
        Battle.battleLoop(player, this);
    }
}
