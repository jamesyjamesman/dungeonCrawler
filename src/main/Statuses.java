package main;

import main.swing.SwingRenderer;

public class Statuses {
    private int poison;
    private int cursed;
    private int fire;
    private int weakened;
    public Statuses() {
        this.poison = 0;
        this.cursed = 0;
        this.fire = 0;
        this.weakened = 0;
    }

    public int getPoison() {
        return this.poison;
    }
    public int getCursed() {
        return this.cursed;
    }
    public int getFire() {
        return this.fire;
    }
    public int getWeakened() {
        return this.weakened;
    }
    public void setPoison(int poison) {
        this.poison = poison;
        SwingRenderer.UIUpdater(App.INSTANCE.getPlayer());
    }
    public void addPoison(int poison) {
        this.poison += poison;
        SwingRenderer.UIUpdater(App.INSTANCE.getPlayer());
    }
    public void setCursed(int cursed) {
        this.cursed = cursed;
        SwingRenderer.UIUpdater(App.INSTANCE.getPlayer());
    }
    public void addCursed(int cursed) {
        this.cursed += cursed;
        SwingRenderer.UIUpdater(App.INSTANCE.getPlayer());
    }
    public void setFire(int fire) {
        this.fire = fire;
        SwingRenderer.UIUpdater(App.INSTANCE.getPlayer());
    }
    public void addFire(int fire) {
        this.fire += fire;
        SwingRenderer.UIUpdater(App.INSTANCE.getPlayer());
    }
    public void setWeakened(int weakened) {
        this.weakened = weakened;
        SwingRenderer.UIUpdater(App.INSTANCE.getPlayer());
    }
    public void addWeakened(int weakened) {
        this.weakened += weakened;
        SwingRenderer.UIUpdater(App.INSTANCE.getPlayer());
    }

}
