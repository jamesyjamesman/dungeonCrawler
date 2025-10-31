package main;

import main.entity.Player;
import main.item.relic.Relic;
import main.room.Room;

import javax.swing.*;
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

    private JFrame frame;

    private Player player;

    private ArrayList<Relic> unusedRelics;

    private ArrayList<Room> rooms;

    private State state = State.DEFAULT;

    public App getInstance() {
        return INSTANCE;
    }
    public JFrame getFrame() {
        return frame;
    }
    public void setFrame(JFrame newFrame) {
        frame = newFrame;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player newPlayer) {
        player = newPlayer;
    }
    public ArrayList<Relic> getUnusedRelics() {
        return unusedRelics;
    }
    public void setUnusedRelics(ArrayList<Relic> newUnusedRelics) {
        unusedRelics = newUnusedRelics;
    }
    public ArrayList<Room> getRooms() {
        return rooms;
    }
    public void setRooms(ArrayList<Room> newRooms) {
        rooms = newRooms;
    }
    public void setState(State state) {
        this.state = state;
    }
    public State getState() {
        return this.state;
    }
}