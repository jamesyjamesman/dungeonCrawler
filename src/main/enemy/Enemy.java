package main.enemy;

import main.DialogueType;
import main.Main;
import main.Player;

import java.util.Random;

public class Enemy {
    int maxHealth;
    int currentHealth;
    int damage;
    int armor;
    int evasiveness;
    int experienceDropped;
    String species;
    String damageType;
    public Enemy() {
        this.maxHealth = 0;
        this.currentHealth = 0;
        this.damage = 0;
        this.armor = 0;
        this.evasiveness = 0;
        this.experienceDropped = 0;
        this.species = "";
        this.damageType = "";
    }

    public int takeDamage(int damage) {
        int oldHealth = this.currentHealth;
        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
            return oldHealth;
        }
        return damage;
    }

    public void attack(Player player) {
        if (player.equippedRelicIndex("Relic of Bounciness") > -1
            && new Random().nextInt(4) == 0) {
                System.out.println(Main.colorString("The attack from the " + this.species + " bounced right off!", DialogueType.BATTLE));
                return;
        }
        System.out.println(Main.colorString("The " + this.species + " attacked you, dealing " + this.damage + " damage!", DialogueType.DAMAGE));
        player.takeDamage(this.damage);
        //will get more complex with weapons, etc.
    }

    public void reset() {
        this.currentHealth = this.maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = this.maxHealth;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public void setSpecies(String species) {
        this.species = species;
    }
    public String getSpecies() {
        return this.species;
    }
    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }
    public int getCurrentHealth() {
        return this.currentHealth;
    }
    public int getMaxHealth(){return this.maxHealth;}
    public void setExperienceDropped(int experienceDropped) {this.experienceDropped = experienceDropped;}
    public int getExperienceDropped() {return this.experienceDropped;}
}
