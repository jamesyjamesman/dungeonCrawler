package main;

public abstract class Entity {
    private int maxHealth;
    private int currentHealth;
    private int damage;

    public Entity() {}
    public Entity(int maxHealth, int currentHealth, int damage) {
        this.maxHealth = maxHealth;
        this.currentHealth = currentHealth;
        this.damage = damage;
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public void increaseMaxHealth(int health) {
        this.maxHealth += health;
        this.currentHealth += health;
    }

    public void increaseDamage(int damageIncrease) {
        this.damage += damageIncrease;
    }

    public int heal(int health) {
        this.currentHealth += health;
        if (this.currentHealth > this.maxHealth) {
            int healthHealed = health - (this.currentHealth - this.maxHealth);
            this.currentHealth = this.maxHealth;
            return healthHealed;
        }
        return health;
    }

    public void takeDamage(int damage) {
        this.currentHealth -= damage;
        if (this.currentHealth <= 0) {
            //todo: die
        }
    }
}
