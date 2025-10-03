package main.enemy;

public class MageEnemy extends Enemy {
    int maxMana;
    int currentMana;
    public MageEnemy() {
        this.setDamageType("magic");
        this.maxMana = 0;
        this.currentMana = 0;
    }
}
