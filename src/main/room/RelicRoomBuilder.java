package main.room;

public class RelicRoomBuilder<T extends RelicRoomBuilder<T>> extends RoomBuilder<T> {

    protected boolean hasCorpse;

    public T hasCorpse(boolean hasCorpse) {
        this.hasCorpse = hasCorpse;
        return self();
    }

    @Override
    public RelicRoom build() {
        return new RelicRoom(this);
    }
}
