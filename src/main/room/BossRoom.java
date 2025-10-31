package main.room;

import main.entity.Player;

public class BossRoom extends EnemyRoom {
    boolean completed;
    public BossRoom(EnemyRoomBuilder<?> builder, int maxEnemies) {
        super(builder, maxEnemies);
        this.completed = false;
    }

    @Override
    public void roomActivator(Player player) {
        if (this.completed) {return;}
        super.roomActivator(player);
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        this.setActive(false);
        this.completed = true;
    }
}
