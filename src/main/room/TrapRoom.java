package main.room;

import main.Player;
import main.SwingRenderer;

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
        SwingRenderer.addHealthText(frame, "You took " + this.damageDealt + " damage!");
        player.takeDamage(frame, this.damageDealt);
    }
    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }
}
