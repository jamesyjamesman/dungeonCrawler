package main.enemy;

import main.Player;
import main.item.relic.RelicType;
import main.room.EnemyRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import javax.swing.*;
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

    public int takeDamage(JFrame frame, int damage) {
        int oldHealth = this.currentHealth;
        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
            return oldHealth;
        }
        return damage;
    }

    public void death(JFrame frame, Player player, EnemyRoom enemyRoom) {
        player.changeExperience(getExperienceDropped());
        player.checkLevelUp(frame, "");
        player.checkStatus(frame);
        SwingRenderer.appendMainLabelText(frame, "The " + getSpecies() + " died! You got " + getExperienceDropped() + " experience!\n", false);

        player.addGold(this.getLoot().getGold());
        for (int i = 0; i < this.getLoot().getItems().size(); i++) {
            player.itemPickup(frame, this.getLoot().getItems().get(i));
        }

        enemyRoom.addDefeatedEnemy(this);
        enemyRoom.removeEnemy(this);
        player.checkInventory(frame);
    }

    public void attack(JFrame frame, Player player) {
        if (player.equippedRelicIndex(RelicType.SLIME) > -1
            && new Random().nextInt(4) == 0) {
            SwingRenderer.addHealthText(frame, "The attack from the " + this.species + " bounced right off!");
                return;
        }
        SwingRenderer.addHealthText(frame, "The " + this.species + " attacked you, dealing " + this.damage + " damage!");
        player.takeDamage(frame, this.damage);
        //will get more complex with weapons, etc.
    }

    //very basic right now, could add a basic description, and attack descriptions for bosses.
    public void checkInformation(JFrame frame) {
        String output = "";
        output += this.species.substring(0, 1).toUpperCase() + this.species.substring(1) + "\n";
        output += "HP: " + this.currentHealth + "/" + this.maxHealth + "\n";
        output += "EXP: " + this.experienceDropped + "\n";
        output += "ATK DMG: " + this.damage + "\n";
        SwingRenderer.changeLabelText(frame, output, ComponentType.LABEL_DESCRIPTION);
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
}
