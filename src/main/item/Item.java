package main.item;

import main.Player;
import main.item.health.PureAppleItem;
import main.item.relic.Relic;
import main.room.PureWaterRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import javax.swing.*;

public abstract class Item implements Cloneable {
    String description;
    String name;
    int value;
    boolean stackable;
    double dropChance;
    public Item() {
        this.description = "";
        this.name = "";
        this.value = 0;
        this.stackable = true;
        this.dropChance = 1.0;
    }

    public void useItem(JFrame frame, Player player) {
       SwingRenderer.UIUpdater(frame, player);
    }

    public void cleanseItem(JFrame frame, Player player) {
        if (this instanceof Relic relic && relic.isCursed()) {
            relic.setCursed(false);
            SwingRenderer.changeLabelText(frame, "The " + relic.getName() + " was cured!", ComponentType.LABEL_ERROR);
            if (relic.isEquipped(player)) {
                player.getCurrentStatuses().setCursed(player.getCurrentStatuses().getCursed() - 1);
            }
        } else if (this.getName().equals("Apple")) {
            player.discardItem(frame, this);
            player.addItemToInventory(frame, new PureAppleItem());
            SwingRenderer.changeLabelText(frame, "The apple was purified!", ComponentType.LABEL_ERROR);
        } else {
            SwingRenderer.changeLabelText(frame, "You put the " + this.getName() + " in the fountain, but nothing happened.", ComponentType.LABEL_ERROR);
        }
        ((PureWaterRoom) player.getCurrentRoom()).setFountainUsed(true);
        SwingRenderer.appendMainLabelText(frame, "The fountain ran dry!", false);
        SwingRenderer.UIUpdater(frame, player);
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

    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
