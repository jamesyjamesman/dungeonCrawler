package main.room;

import main.entity.enemy.Enemy;

import java.util.ArrayList;

public class EnemyRoomBuilder<T extends EnemyRoomBuilder<T>> extends RoomBuilder<T> {
    ArrayList<Enemy> allowedEnemies;
    boolean spawnExact = false;
    int maxEnemies;
    String battleInitiationMessage;

    public T allowedEnemies(ArrayList<Enemy> allowedEnemies) {
        this.allowedEnemies = allowedEnemies;
        return self();
    }

    public T allowedEnemies(Enemy enemy) {
        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);
        this.allowedEnemies = enemies;
        return self();
    }

    public T battleInitiationMessage(String battleInitiationMessage) {
        this.battleInitiationMessage = battleInitiationMessage;
        return self();
    }

    public T maxEnemies(int maxEnemies) {
        this.maxEnemies = maxEnemies;
        return self();
    }

    public T spawnExact() {
        this.spawnExact = true;
        return self();
    }

    @Override
    public EnemyRoom build() {
        return new EnemyRoom(this, this.maxEnemies, this.spawnExact);
    }

    public BossRoom buildBoss() {
        return new BossRoom(this, this.maxEnemies, this.spawnExact);
    }


}
