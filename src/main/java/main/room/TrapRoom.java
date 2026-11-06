package main.room;

import main.entity.Player;

public class TrapRoom extends Room {
    private final int damageDealt;

    public TrapRoom(TrapRoomBuilder<?> builder) {
        super(builder);
        this.damageDealt = builder.damageDealt != 0 ? builder.damageDealt : 3;
    }

    public int getDamageDealt() {
        return this.damageDealt;
    }

    public int damage(Player player) {
        return player.takeDamage(this.damageDealt);
    }
}