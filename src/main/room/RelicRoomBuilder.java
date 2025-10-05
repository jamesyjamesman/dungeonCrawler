package main.room;

public class RelicRoomBuilder extends RoomBuilder {

    protected boolean hasCorpse;

    public RelicRoomBuilder hasCorpse(boolean hasCorpse) {
        this.hasCorpse = hasCorpse;
        return this;
    }

    @Override
    public Room build() {
        return new RelicRoom(this);
    }
}
