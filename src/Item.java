public abstract class Item {
    String description;
    String name;
    public Item() {
        this.description = "";
        this.name = "";
    }

    public abstract void useItem(Player player);
}