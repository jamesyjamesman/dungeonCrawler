package main.item.relic;

import main.DialogueType;
import main.Main;
import main.Player;
import main.item.Item;
import main.room.Room;

import java.util.Random;

public abstract class Relic extends Item {
    boolean equipped;
    boolean cursed;
    public Relic(){
        this.equipped = false;
        this.cursed = new Random().nextInt(5) == 0;
    }

    public void useRelic(Player player, Room room) {
        if (this.cursed) {
            if (player.equippedRelicIndex("Relic of Cursed Healing") == -1) {
                int amountDamage = new Random().nextInt(4);
                if (amountDamage > 0) {
                    System.out.println(Main.colorString("Your cursed " + getName() + " caused " + amountDamage + " damage!", DialogueType.DAMAGE));
                    player.takeDamage(amountDamage);
                }
            } else {
                int amountHeal = new Random().nextInt(2);
                if (amountHeal > 0) {
                    System.out.println(Main.colorString("Your cursed " + getName() + " healed you for " + amountHeal + " health!", DialogueType.HEAL));
                    player.heal(amountHeal);
                }
            }
        }
    }

    @Override
    public void useItem(Player player) {
        if(this.equipped) {player.unequipRelic(this);}
        else {player.equipRelic(this);}
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
