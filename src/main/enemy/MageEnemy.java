package main.enemy;

public class MageEnemy extends Enemy {
    int maxMana;
    int currentMana;
    public MageEnemy() {
        this.damageType = "magic";
        this.maxMana = 0;
        this.currentMana = 0;
    }
}
