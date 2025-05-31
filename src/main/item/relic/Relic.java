package main.item.relic;

import main.Player;
import main.swing.SwingRenderer;
import main.item.Item;
import main.room.Room;

import javax.swing.*;
import java.util.Random;

public abstract class Relic extends Item {
    boolean equipped;
    boolean cursed;
    public Relic(){
        this.equipped = false;
        this.cursed = new Random().nextInt(5) == 0;
    }

    public void useRelic(JFrame frame, Player player, Room room) {
        if (this.cursed) {
            if (player.equippedRelicIndex("Relic of Cursed Healing") == -1) {
                int amountDamage = new Random().nextInt(4);
                if (amountDamage > 0) {
                    SwingRenderer.addHealthText(frame, "Your cursed " + getName() + " caused " + amountDamage + " damage!");
                    player.takeDamage(frame, amountDamage);
                }
            } else {
                int amountHeal = new Random().nextInt(2);
                if (amountHeal > 0) {
                    SwingRenderer.addHealthText(frame, "Your cursed " + getName() + " healed you for " + amountHeal + " health!");
                    player.heal(amountHeal);
                }
            }
        }
    }

    @Override
    public void useItem(JFrame frame, Player player) {
        if(this.equipped) {player.unequipRelic(frame, this);}
        else {player.equipRelic(frame, this);}
    }

    public boolean isCursed() {
        return this.cursed;
    }

    public void setCursed(boolean cursed) {
        this.cursed = cursed;
    }
    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
