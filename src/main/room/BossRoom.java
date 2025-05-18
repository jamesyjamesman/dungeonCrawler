package main.room;

import main.Player;

public class BossRoom extends EnemyRoom {
    int roomsRequired;
    boolean completed;
    public BossRoom() {
        this.roomsRequired = 0;
        this.active = false;
        this.completed = false;
    }
    public void setActiveIfRooms(Player player) {
        if (this.completed) {return;}
        if (player.getRoomsTraversed() >= getRoomsRequired()) {
            this.active = true;
        }
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        this.active = false;
        this.completed = true;
    }

    public void setRoomsRequired(int roomsRequired) {
        this.roomsRequired = roomsRequired;
    }
    public int getRoomsRequired() {
        return roomsRequired;
    }
}
