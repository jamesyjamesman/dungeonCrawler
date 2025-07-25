package main.item;

public class ItemBlueprint {
    ItemID itemID;
    double dropChance;
    public ItemBlueprint(double dropChance, ItemID itemID) {
        this.dropChance = dropChance;
        this.itemID = itemID;
    }

    public ItemID getID() {
        return this.itemID;
    }
    public double getDropChance() {
        return this.dropChance;
    }
}
