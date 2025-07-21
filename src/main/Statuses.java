package main;

public class Statuses {
    int poison;
    int cursed;
    int fire;
    int weakened;
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
    }
    public void setCursed(int cursed) {
        this.cursed = cursed;
    }
    public void setFire(int fire) {
        this.fire = fire;
    }
    public void setWeakened(int weakened) {
        this.weakened = weakened;
    }

}
