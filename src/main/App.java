package main;

import javax.swing.*;

public enum App {

    INSTANCE();

    public JFrame frame;

    public Player player;

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
}