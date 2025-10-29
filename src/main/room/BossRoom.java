package main.room;

import main.entity.Player;

public class BossRoom extends EnemyRoom {
    boolean completed;
    public BossRoom(EnemyRoomBuilder<?> builder) {
        super(builder);
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
