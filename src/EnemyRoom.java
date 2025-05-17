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
        addEnemies(this.defeatedEnemies);
        for (Enemy enemy : this.enemies) {enemy.reset();}
        this.defeatedEnemies.clear();
    }
    public void addEnemies(ArrayList<Enemy> enemies) {
        this.enemies.addAll(enemies);
    }
    public void addEnemies(Enemy enemy) {
        this.enemies.add(enemy);
    }
    public void setBattleInitiationMessage(String battleInitiationMessage) {
        this.battleInitiationMessage = battleInitiationMessage;
    }

}
