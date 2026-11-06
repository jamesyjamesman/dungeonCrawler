package main;

import io.javalin.Javalin;
import main.entity.Player;
import main.item.relic.Relic;
import main.room.Room;

import java.util.ArrayList;

public enum App {

    INSTANCE();

    public enum State {
        DEFAULT,
        ROOM_END,
        BATTLE_START,
        PLAYER_TURN,
        ENEMY_TURN
    }

    private Javalin server;

    private Player player;

    private ArrayList<Relic> findableRelics;

    private ArrayList<Room> rooms;

    private State state = State.DEFAULT;

    public App getInstance() {
        return INSTANCE;
    }
    public Javalin getServer() {
        return this.server;
    }
    public void setServer(Javalin server) {
        this.server = server;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player newPlayer) {
        player = newPlayer;
    }
    public ArrayList<Relic> getFindableRelics() {
        return findableRelics;
    }
    public void setFindableRelics(ArrayList<Relic> newUnusedRelics) {
        findableRelics = newUnusedRelics;
    }
    public ArrayList<Relic> getObtainableFindableRelics() {
        return new ArrayList<>(findableRelics.stream()
                .filter(relic -> !this.getPlayer().getAllRelics().contains(relic))
                .toList());
    }
    public ArrayList<Room> getRooms() {
        return this.rooms;
    }
    public void setRooms(ArrayList<Room> newRooms) {
        this.rooms = newRooms;
    }
    public void setState(State state) {
        this.state = state;
    }
    public State getState() {
        return this.state;
    }
}