package main.entity.enemy;

import main.App;
import main.entity.Entity;
import main.entity.Player;
import main.entity.Species;
import main.item.Loot;
import main.item.relic.RelicID;
import main.item.relic.SlimeRelic;
import main.room.EnemyRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.Random;

public class Enemy extends Entity {
//    private int armor;
//    private int evasiveness;
    private final int experienceDropped;
    //lowest level the player can be to encounter the enemy
    private final int minimumLevel;
    private final String damageType;
    private Loot loot;
    public Enemy(Species species, int health, int damage, int experienceDropped, int minimumLevel) {
        this(species, health, damage, experienceDropped, minimumLevel, new Loot());
    }

    public Enemy(Species species, int health, int damage, int experienceDropped, int minimumLevel, Loot loot) {
        super(species, health, damage);
        this.experienceDropped = experienceDropped;
        this.minimumLevel = minimumLevel;
//        this.armor = 0;
//        this.evasiveness = 0;
        this.damageType = "";
        this.loot = loot;
    }

    @Override
    public void die() {
        Player player = App.INSTANCE.getPlayer();
        EnemyRoom enemyRoom = (EnemyRoom) player.getCurrentRoom();

        player.changeExperience(getExperienceDropped());
        player.levelUp();
        player.checkStatus();
        SwingRenderer.appendLabelText("The " + speciesToStringLower() + " died! You got " + getExperienceDropped() + " experience!\n", false, ComponentType.LABEL_DESCRIPTION);

        this.getLoot().dropLoot(player);

        enemyRoom.removeEnemy(this);
        player.checkInventory();
    }

    public void attack(Player player) {
        // todo this should probably be in player?
        if (SlimeRelic.slimeBounce(player)) {
            SwingRenderer.addHealthText("The attack from the " + speciesToStringLower() + " bounced right off!");
            return;
        }

        SwingRenderer.addHealthText("The " + speciesToStringLower() + " attacked you, dealing " + getDamage() + " damage!");
        player.takeDamage(getDamage());
        //will get more complex with weapons, etc.
    }

    //very basic right now, could add a basic description, and attack descriptions for bosses.
    public void checkInformation() {
        String output = "";
        output += speciesToString().charAt(0) + speciesToString().substring(1).toLowerCase() + "\n";
        output += "HP: " + getCurrentHealth() + "/" + getMaxHealth() + "\n";
        output += "EXP: " + this.experienceDropped + "\n";
        output += "ATK DMG: " + getDamage() + "\n";
        SwingRenderer.appendLabelText(output, true, ComponentType.LABEL_DESCRIPTION);
    }

    public void reset() {
        heal(getMaxHealth());
    }

    public int getExperienceDropped() {return this.experienceDropped;}
    public Loot getLoot() {
        return this.loot;
    }
    public void setLoot(Loot loot) {
        this.loot = loot;
    }
    public int getMinimumLevel() {
        return this.minimumLevel;
    }
}
