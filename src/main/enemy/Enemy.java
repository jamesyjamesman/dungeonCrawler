package main.enemy;

import main.Player;
import main.item.Loot;
import main.item.relic.RelicID;
import main.room.EnemyRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

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
    Loot loot;
    public Enemy() {
        this.maxHealth = 0;
        this.currentHealth = 0;
        this.damage = 0;
        this.armor = 0;
        this.evasiveness = 0;
        this.experienceDropped = 0;
        this.species = "";
        this.damageType = "";
        this.loot = new Loot();
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

    public void death(Player player, EnemyRoom enemyRoom) {
        player.changeExperience(getExperienceDropped());
        player.levelUp();
        player.checkStatus();
        SwingRenderer.appendLabelText("The " + getSpecies() + " died! You got " + getExperienceDropped() + " experience!\n", false, ComponentType.LABEL_DESCRIPTION);

        this.getLoot().dropLoot(player);

        enemyRoom.addDefeatedEnemy(this);
        enemyRoom.removeEnemy(this);
        player.checkInventory();
    }

    public void attack(Player player) {
        if (player.equippedRelicIndex(RelicID.SLIME) != -1
            && new Random().nextInt(4) == 0) {
            SwingRenderer.addHealthText("The attack from the " + this.species + " bounced right off!");
                return;
        }
        SwingRenderer.addHealthText("The " + this.species + " attacked you, dealing " + this.damage + " damage!");
        player.takeDamage(this.damage);
        //will get more complex with weapons, etc.
    }

    //very basic right now, could add a basic description, and attack descriptions for bosses.
    public void checkInformation() {
        String output = "";
        output += this.species.substring(0, 1).toUpperCase() + this.species.substring(1) + "\n";
        output += "HP: " + this.currentHealth + "/" + this.maxHealth + "\n";
        output += "EXP: " + this.experienceDropped + "\n";
        output += "ATK DMG: " + this.damage + "\n";
        SwingRenderer.appendLabelText(output, true, ComponentType.LABEL_DESCRIPTION);
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
    public Loot getLoot() {
        return this.loot;
    }
    public void setLoot(Loot loot) {
        this.loot = loot;
    }
}
