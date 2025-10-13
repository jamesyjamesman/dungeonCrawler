package main.room;

import main.enemy.Enemy;

import java.util.ArrayList;

public class EnemyRoomBuilder<T extends EnemyRoomBuilder<T>> extends RoomBuilder<T> {
    ArrayList<Enemy> enemies;
    String battleInitiationMessage;

    public T enemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
        return self();
    }

    public T battleInitiationMessage(String battleInitiationMessage) {
        this.battleInitiationMessage = battleInitiationMessage;
        return self();
    }

    @Override
    public EnemyRoom build() {
        return new EnemyRoom(this);
    }


}
