package main.room;

import main.Battle;
import main.Player;
import main.enemy.Enemy;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.ArrayList;

public class EnemyRoom extends Room {
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Enemy> defeatedEnemies;
    private final String battleInitiationMessage;

    public EnemyRoom(EnemyRoomBuilder<?> builder) {
        super(builder);
        this.defeatedEnemies = new ArrayList<>();
        this.enemies = builder.enemies != null ? builder.enemies : new ArrayList<>();
        this.battleInitiationMessage = builder.battleInitiationMessage != null ? builder.battleInitiationMessage : "";
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        SwingRenderer.appendTextPane(this.battleInitiationMessage, true, ComponentType.PANE_MAIN);
        Battle.battleLoop(player, this);
        reset();
    }

    public void reset() {
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
    public void removeEnemy(Enemy enemy) {
        this.enemies.remove(enemy);
    }
    public void addDefeatedEnemy(Enemy enemy) {
        this.defeatedEnemies.add(enemy);
    }
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }
}
