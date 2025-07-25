package main.item.weapon;

import main.Player;
import main.item.Item;

import javax.swing.*;
import java.util.Random;

//should be abstract
public class Weapon extends Item {
    int damage;
    WeaponType weaponType;
    public Weapon() {
        this.damage = 0;
        setStackable(false);
        this.weaponType = null;
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        System.out.println(this.isEquipped(player));
        if (this.isEquipped(player)) {
            player.setEquippedWeapon(frame, null);
        } else {
            player.setEquippedWeapon(frame, this);
        }
        UIUpdater(frame, player);
    }

    @Override
    public void dropItem(JFrame frame, Player player) {
        if (new Random().nextDouble(0, 1) < this.getDropChance()) {
            player.itemPickup(frame, duplicateWeapon(this.weaponType));
        }
    }

    //this is silly?? but i don't know what else to do here
    public static Weapon duplicateWeapon(WeaponType type) {
        return switch (type) {
            case MACE -> new Mace(1.0);
            case SHORT_SWORD -> new ShortSword(1.0);
            case WAND -> new Wand(1.0);
        };
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
    public boolean isEquipped(Player player) {
        return this == player.getEquippedWeapon();
    }
    public int getDamage() {
        return damage;
    }
    public WeaponType getType() {
        return this.weaponType;
    }
    public void setType(WeaponType type) {
        this.weaponType = type;
    }
}
