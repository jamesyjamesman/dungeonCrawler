package main.item;

import main.Player;
import main.room.Room;

import java.util.Random;

public abstract class Relic extends Item{
    boolean equipped;
    boolean cursed;
    public Relic(){
        this.equipped = false;
        this.cursed = new Random().nextInt(5) == 0;
    }

    public void useRelic(Player player, Room room) {
        if (this.cursed) {
            int amountDamage = new Random().nextInt(5);
            System.out.println("Your cursed " + this.name + " caused 1 damage!");
            player.takeDamage(amountDamage);
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
