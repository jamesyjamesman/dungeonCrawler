package main.room;

import main.App;
import main.Battle;
import main.entity.Player;
import main.entity.enemy.Enemy;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.ArrayList;
import java.util.Random;

public class EnemyRoom extends Room {
    private final ArrayList<Enemy> allowedEnemies;
    private ArrayList<Enemy> enemies;
    private final String battleInitiationMessage;
    private final int maxEnemies;
    private final boolean spawnExact;

    public EnemyRoom(EnemyRoomBuilder<?> builder, int maxEnemies, boolean spawnExact) {
        super(builder);
        this.maxEnemies = maxEnemies;
        this.spawnExact = spawnExact;
        this.allowedEnemies = builder.allowedEnemies;
        this.enemies = randomizeEnemies(maxEnemies);
        this.battleInitiationMessage = builder.battleInitiationMessage != null ? builder.battleInitiationMessage : "";
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        SwingRenderer.appendTextPane(this.battleInitiationMessage, true, ComponentType.PANE_MAIN);
        Battle.battleLoop(player, this);
        this.enemies = randomizeEnemies(this.maxEnemies);
        for (Enemy enemy : this.enemies) {
            enemy.reset();
        }
    }

    public ArrayList<Enemy> randomizeEnemies(int maxEnemies) {
        if (this.spawnExact) {
            return this.allowedEnemies;
        }
        int numEnemies = new Random().nextInt(maxEnemies) + 1;
        ArrayList<Enemy> levelCompliantEnemies = new ArrayList<>(
                this.allowedEnemies.stream()
                .filter(enemy -> App.INSTANCE.getPlayer().getLevel() >= enemy.getMinimumLevel())
                .toList());
        ArrayList<Enemy> newEnemies = new ArrayList<>();
        while (newEnemies.size() < numEnemies) {
            newEnemies.add(levelCompliantEnemies.get(new Random().nextInt(levelCompliantEnemies.size())));
        }
        return newEnemies;
    }

    public void removeEnemy(Enemy enemy) {
        this.enemies.remove(enemy);
    }
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }
    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }
}
