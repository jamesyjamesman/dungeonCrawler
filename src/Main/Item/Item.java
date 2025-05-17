package Main.Item;

import Main.Player;

public abstract class Item {
    String description;
    String name;
    public Item() {
        this.description = "";
        this.name = "";
    }

    public abstract void useItem(Player player);


    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {return this.description;}
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {return this.name;}
}