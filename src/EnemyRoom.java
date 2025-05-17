import java.util.ArrayList;

public class EnemyRoom extends Room {
    ArrayList<Enemy> enemies;
    ArrayList<Enemy> defeatedEnemies;
    String battleInitiationMessage;
    public EnemyRoom() {
        super();
        this.enemies = new ArrayList<>();
        this.defeatedEnemies = new ArrayList<>();
        this.battleInitiationMessage = "";
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        System.out.println(this.battleInitiationMessage);
        Battle.battleLoop(player, this);
        for (int i = 0; i < this.defeatedEnemies.size(); i++) {
            this.enemies.add(this.defeatedEnemies.get(i));
            this.enemies.get(i).reset();
        }
        this.defeatedEnemies.clear();
    }


}
