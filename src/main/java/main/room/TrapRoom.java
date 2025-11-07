package main.room;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import main.entity.Player;

public class TrapRoom extends Room {
    private final int damageDealt;

    public TrapRoom(TrapRoomBuilder<?> builder) {
        super(builder);
        this.damageDealt = builder.damageDealt != 0 ? builder.damageDealt : 3;
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        player.takeDamage(this.damageDealt);
    }

    @JsonSerialize
    public int getDamageDealt() {
        return this.damageDealt;
    }
}