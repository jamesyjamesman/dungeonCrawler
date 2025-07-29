package main.room;

import main.Player;

import javax.swing.*;

public class BossRoom extends EnemyRoom {
    boolean completed;
    public BossRoom() {
        this.active = false;
        this.completed = false;
    }

    @Override
    public void roomActivator(Player player) {
        if (this.completed) {return;}
        super.roomActivator(player);
    }

    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);
        this.active = false;
        this.completed = true;
    }
}
