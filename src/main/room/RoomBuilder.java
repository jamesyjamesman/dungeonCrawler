package main.room;

    public class RoomBuilder {
        protected String appearance;
        protected String description;
        protected String backgroundFileName;
        protected int id;
        protected int numExits;
        protected RoomType type;
        protected int roomsRequired;
        protected int selectionWeight;

        public RoomBuilder appearance(String appearance) {
            this.appearance = appearance;
            return this;
        }

        public RoomBuilder description(String description) {
            this.description = description;
            return this;
        }

        public RoomBuilder backgroundFileName(String backgroundFileName) {
            this.backgroundFileName = backgroundFileName;
            return this;
        }

        public RoomBuilder id(int id) {
            this.id = id;
            return this;
        }

        public RoomBuilder numExits(int numExits) {
            this.numExits = numExits;
            return this;
        }

        public RoomBuilder type(RoomType type) {
            this.type = type;
            return this;
        }

        public RoomBuilder roomsRequired(int roomsRequired) {
            this.roomsRequired = roomsRequired;
            return this;
        }

        public RoomBuilder selectionWeight(int selectionWeight) {
            this.selectionWeight = selectionWeight;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
}