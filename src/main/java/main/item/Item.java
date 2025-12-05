package main.item;

import main.Identifiable;
import main.entity.Player;
import main.item.health.PureAppleItem;
import main.room.PureWaterRoom;

public abstract class Item extends Identifiable implements Cloneable {
    private final String description;
    private final String name;
    private final int value;
    private final boolean stackable;
    private final double dropChance;
    private final int shopWeight;
    private final boolean cleansable;
    private final ItemType type;
    public Item(String name, String description, int value, boolean stackable, double dropChance, int shopWeight, ItemType type) {
        this(name, description, value, stackable, dropChance, shopWeight, type, false);
    }

    public Item(String name, String description, int value, boolean stackable, double dropChance, int shopWeight, ItemType type, boolean cleansable) {
        this.description = description;
        this.name = name;
        this.value = value;
        this.stackable = stackable;
        this.dropChance = dropChance;
        this.shopWeight = shopWeight;
        this.type = type;
        this.cleansable = cleansable;
    }

    public abstract String useItem(Player player);

    public boolean cleanseItem(Player player) {
        ((PureWaterRoom) player.getCurrentRoom()).setFountainUsed(true);
        if (this.getName().equals("Apple")) {
            player.discardItem(this);
            player.addItemToInventory(new PureAppleItem());
            return true;
        }
        return false;
    }


    public String getDescription() {
        return this.description;
    }
    public String getName() {
        return this.name;
    }
    public int getValue() {
        return this.value;
    }
    public boolean isStackable() {
        return this.stackable;
    }
    public boolean isCleansable() {
        return this.cleansable;
    }
    public ItemType getType() {
        return this.type;
    }
    public double getDropChance() {
        return this.dropChance;
    }
    public int getShopWeight() {
        return this.shopWeight;
    }

    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
