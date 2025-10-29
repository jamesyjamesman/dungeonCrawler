package main;

import main.entity.Player;
import main.item.relic.Relic;
import main.room.Room;

import javax.swing.*;
import java.util.ArrayList;

public enum App {

    INSTANCE();

    private JFrame frame;

    private Player player;

    private ArrayList<Relic> unusedRelics;

    private ArrayList<Room> rooms;

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
}