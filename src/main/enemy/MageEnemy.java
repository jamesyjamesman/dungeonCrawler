package main.enemy;

public class MageEnemy extends Enemy {
    private int maxMana;
    private int currentMana;
    public MageEnemy() {
        this.setDamageType("magic");
        this.maxMana = 0;
        this.currentMana = 0;
    }
}
