package main.entity.enemy;

import main.App;
import main.entity.Entity;
import main.entity.Player;
import main.entity.Species;
import main.item.Loot;
import main.item.relic.SlimeRelic;
import main.room.EnemyRoom;

public class Enemy extends Entity implements Cloneable {
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
        // todo call this somewhere else (check level up request)
        player.levelUp();
        player.checkStatus();

        enemyRoom.removeEnemy(this);
    }

    public String attack(Player player) {
        if (SlimeRelic.slimeBounce(player)) {
            return "The attack from the " + this.speciesToStringLower() + " bounced right off!";
        }

        player.takeDamage(getDamage());
        return "You took " + getDamage() + " damage from the " + this.speciesToStringLower() + "!";
        //will get more complex with weapons, etc.
    }

    //very basic right now, could add a basic description, and attack descriptions for bosses.
    public void checkInformation() {
        String output = "";
        output += speciesToString().charAt(0) + speciesToString().substring(1).toLowerCase() + "\n";
        output += "HP: " + getCurrentHealth() + "/" + getMaxHealth() + "\n";
        output += "EXP: " + this.experienceDropped + "\n";
        output += "ATK DMG: " + getDamage() + "\n";
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

    @Override
    public Enemy clone() {
        try {
            return (Enemy) super.clone();
            //loot doesnt need to be cloned because it works like a blueprint
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
