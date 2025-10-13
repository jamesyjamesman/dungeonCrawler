package main.room;

    public class RoomBuilder<T extends RoomBuilder<T>> {
        protected String appearance;
        protected String description;
        protected String backgroundFileName;
        protected int id;
        protected int numExits;
        protected RoomType type;
        protected int roomsRequired;
        protected int selectionWeight;

        public T self() {
            return (T) this;
        }

        public T appearance(String appearance) {
            this.appearance = appearance;
            return self();
        }

        public T description(String description) {
            this.description = description;
            return self();
        }

        public T backgroundFileName(String backgroundFileName) {
            this.backgroundFileName = backgroundFileName;
            return self();
        }

        public T id(int id) {
            this.id = id;
            return self();
        }

        public T numExits(int numExits) {
            this.numExits = numExits;
            return self();
        }

        public T type(RoomType type) {
            this.type = type;
            return self();
        }

        public T roomsRequired(int roomsRequired) {
            this.roomsRequired = roomsRequired;
            return self();
        }

        public T selectionWeight(int selectionWeight) {
            this.selectionWeight = selectionWeight;
            return self();
        }

        public Room build() {
            return new Room(this);
        }
}