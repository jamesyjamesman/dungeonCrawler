package main.item;

import main.Player;
import main.item.health.*;
import main.item.relic.*;
import main.item.weapon.*;
import main.item.buff.*;
import main.room.PureWaterRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import javax.swing.*;

public abstract class Item implements Cloneable {
    String description;
    String name;
    int value;
    boolean stackable;
    public Item() {
        this.description = "";
        this.name = "";
        this.value = 0;
        this.stackable = true;
    }

    public void useItem(JFrame frame, Player player) {
       SwingRenderer.UIUpdater(frame, player);
    }

    public static Item itemFactory(ItemID itemID) {
        return switch (itemID) {
            case WEAPON_MACE -> new Mace();
            case WEAPON_SWORD_SHORT -> new ShortSword();
            case WEAPON_WAND -> new Wand();
            case WEAPON_SPEAR_SLIME -> new SlimeSpear();
            case WEAPON_SWORD_SLIME -> new SlimeSword();
            case HEALTH_APPLE -> new AppleItem();
            case HEALTH_STEAK -> new SteakItem();
            case HEALTH_APPLE_PURE -> new PureAppleItem();
            case HEALTH_CHOCOLATE -> new ChocolateItem();
            case BUFF_DAMAGE -> new DamageBuffItem();
            case BUFF_HEALTH -> new HealthBuffItem();
            //TODO: add the rest
            default -> new DummyItem();
        };
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

    //TODO: maybe deal with nested objects (like name and description)
    @Override
    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
