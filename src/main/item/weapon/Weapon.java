package main.item.weapon;

import main.Player;
import main.item.Item;

import javax.swing.*;

public class Weapon extends Item {
    boolean equipped;
    int damage;
    public Weapon() {
        this.equipped = false;
        this.damage = 0;
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        if (this.isEquipped()) {
            this.setEquipped(false);
            player.setEquippedWeapon(null);
        } else {
            this.setEquipped(true);
            player.setEquippedWeapon(this);
        }
        UIUpdater(frame, player);
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
    public boolean isEquipped() {
        return equipped;
    }
    public int getDamage() {
        return damage;
    }
}
