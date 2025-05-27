package main.room;

import main.Battle;
import main.enemy.Enemy;
import main.Player;

import javax.swing.JFrame;
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
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);
        System.out.println(this.battleInitiationMessage);
        Battle.battleLoop(frame, player, this);
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
    public void removeEnemies(Enemy enemy) {
        this.enemies.remove(enemy);
    }
    public void addDefeatedEnemies(Enemy enemy) {
        this.defeatedEnemies.add(enemy);
    }
    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }
    public void setBattleInitiationMessage(String battleInitiationMessage) {
        this.battleInitiationMessage = battleInitiationMessage;
    }

}
