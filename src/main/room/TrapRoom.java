package main.room;

import main.DialogueType;
import main.Main;
import main.Player;

import javax.swing.*;

public class TrapRoom extends Room {
    int damageDealt;
    public TrapRoom() {
        super();
        this.damageDealt = 5;
    }

    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);
        System.out.println(Main.colorString("You took " + this.damageDealt + " damage!", DialogueType.DAMAGE));
        player.takeDamage(this.damageDealt);
    }
    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }
}
