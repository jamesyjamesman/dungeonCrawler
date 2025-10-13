package main.room;

import main.Player;
import main.swing.SwingRenderer;

public class TrapRoom extends Room {
    int damageDealt;

    public TrapRoom(TrapRoomBuilder<?> builder) {
        super(builder);
        this.damageDealt = builder.damageDealt != 0 ? builder.damageDealt : 3;
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        SwingRenderer.addHealthText("You took " + this.damageDealt + " damage!");
        player.takeDamage(this.damageDealt);
    }
}