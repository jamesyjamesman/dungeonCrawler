package main.item;

import main.Player;
import main.item.health.PureAppleItem;
import main.item.relic.Relic;
import main.room.PureWaterRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

public abstract class Item implements Cloneable {
    String description;
    String name;
    int value;
    boolean stackable;
    double dropChance;
    int shopWeight;
    public Item() {
        this.description = "";
        this.name = "";
        this.value = 0;
        this.stackable = true;
        this.dropChance = 1.0;
        this.shopWeight = 10;
    }

    public void useItem(Player player) {
       SwingRenderer.UIUpdater(player);
    }

    public void cleanseItem(Player player) {
        if (this instanceof Relic relic && relic.isCursed()) {
            relic.setCursed(false);
            SwingRenderer.changeLabelText("The " + relic.getName() + " was cured!", ComponentType.LABEL_ERROR);
            if (relic.isEquipped(player)) {
                player.getCurrentStatuses().setCursed(player.getCurrentStatuses().getCursed() - 1);
            }
        } else if (this.getName().equals("Apple")) {
            player.discardItem(this);
            player.addItemToInventory(new PureAppleItem());
            SwingRenderer.changeLabelText("The apple was purified!", ComponentType.LABEL_ERROR);
        } else {
            SwingRenderer.changeLabelText("You put the " + this.getName() + " in the fountain, but nothing happened.", ComponentType.LABEL_ERROR);
        }
        ((PureWaterRoom) player.getCurrentRoom()).setFountainUsed(true);
        SwingRenderer.appendMainLabelText("The fountain ran dry!", false);
        SwingRenderer.UIUpdater(player);
    }


    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return this.description;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }
    public boolean isStackable() {
        return this.stackable;
    }

    public double getDropChance() {
        return this.dropChance;
    }
    public void setDropChance(double dropChance) {
        this.dropChance = dropChance;
    }
    public int getShopWeight() {
        return this.shopWeight;
    }
    public void setShopWeight(int shopWeight) {
        this.shopWeight = shopWeight;
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
