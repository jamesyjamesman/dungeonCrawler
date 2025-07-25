package main.item.weapon;

import main.Player;
import main.item.Item;

import javax.swing.*;

//should be abstract
public class Weapon extends Item {
    int damage;
    public Weapon() {
        this.damage = 0;
        setStackable(false);
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

    public void setDamage(int damage) {
        this.damage = damage;
    }
    public boolean isEquipped(Player player) {
        return this == player.getEquippedWeapon();
    }
    public int getDamage() {
        return damage;
    }
}
