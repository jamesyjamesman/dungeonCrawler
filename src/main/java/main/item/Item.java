package main.item;

import main.Identifiable;
import main.entity.Player;
import main.item.health.PureAppleItem;
import main.item.relic.Relic;
import main.room.PureWaterRoom;

public abstract class Item extends Identifiable implements Cloneable {
    private final String description;
    private final String name;
    private final int value;
    private final boolean stackable;
    private final double dropChance;
    private final int shopWeight;
    public Item(String name, String description, int value, boolean stackable, double dropChance, int shopWeight) {
        this.description = description;
        this.name = name;
        this.value = value;
        this.stackable = stackable;
        this.dropChance = dropChance;
        this.shopWeight = shopWeight;
    }

    public void useItem(Player player) {
    }

    public void cleanseItem(Player player) {
        if (this instanceof Relic relic && relic.isCursed()) {
            relic.setCursed(false);
            if (relic.isEquipped(player)) {
                player.getCurrentStatuses().addCursed(-1);
            }
        } else if (this.getName().equals("Apple")) {
            player.discardItem(this);
            player.addItemToInventory(new PureAppleItem());
        } else {
        }
        ((PureWaterRoom) player.getCurrentRoom()).setFountainUsed(true);
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
