package main.entity;

public abstract class Entity {
    private int maxHealth;
    private int currentHealth;
    private int damage;
    private final Species species;

    public Entity(Species species) {
        this.species = species;
    }
    public Entity(Species species, int health, int damage) {
        this.species = species;
        this.maxHealth = health;
        this.currentHealth = health;
        this.damage = damage;
    }

    public abstract void die();

    public Species getSpecies() {
        return this.species;
    }

    public String speciesToString() {
        return this.species.toString();
    }

    public String speciesToStringLower() {
        return this.species.toString().toLowerCase();
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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

    //returns the damage taken. this is mostly unnecessary, and could potentially be made void.
    public int takeDamage(int damage) {
        int oldHealth = this.currentHealth;
        this.currentHealth -= damage;
        if (this.currentHealth <= 0) {
            this.currentHealth = 0;
            die();
            return oldHealth;
        }
        return damage;
    }
}
