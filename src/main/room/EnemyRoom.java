package main.room;

import main.Battle;
import main.Player;
import main.enemy.Enemy;
import main.swing.SwingRenderer;

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
        SwingRenderer.appendMainLabelText(this.battleInitiationMessage, true);
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
    public void setBattleInitiationMessage(String battleInitiationMessage) {
        this.battleInitiationMessage = battleInitiationMessage;
    }

}
