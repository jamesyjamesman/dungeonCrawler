package main.room;

public class TrapRoomBuilder<T extends TrapRoomBuilder<T>> extends RoomBuilder<T> {
    int damageDealt;

    public T damageDealt(int damageDealt) {
        this.damageDealt = damageDealt;
        return self();
    }

    @Override
    public TrapRoom build() {
        return new TrapRoom(this);
    }
}
