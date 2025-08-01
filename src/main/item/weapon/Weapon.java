package main.item.weapon;

import main.Player;
import main.item.Item;
import main.swing.SwingRenderer;

//should be abstract
public class Weapon extends Item {
    int damage;
    public Weapon() {
        this.damage = 0;
        setStackable(false);
    }

    @Override
    public void useItem(Player player) {
        if (this.isEquipped(player)) {
            player.setEquippedWeapon(null);
        } else {
            player.setEquippedWeapon(this);
        }
        SwingRenderer.UIUpdater(player);
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
