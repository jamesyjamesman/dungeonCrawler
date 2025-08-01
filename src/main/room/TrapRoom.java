package main.room;

import main.Player;
import main.swing.SwingRenderer;

public class TrapRoom extends Room {
    int damageDealt;
    public TrapRoom() {
        super();
        this.damageDealt = 5;
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        SwingRenderer.addHealthText("You took " + this.damageDealt + " damage!");
        player.takeDamage(this.damageDealt);
    }
    public void setDamageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
    }
}
