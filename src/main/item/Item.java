package main.item;

import main.Player;
import main.item.relic.Relic;
import main.room.PureWaterRoom;
import main.swing.LabelType;
import main.swing.SwingRenderer;

import javax.swing.*;

public abstract class Item {
    String description;
    String name;
    public Item() {
        this.description = "";
        this.name = "";
    }

    public abstract void useItem(JFrame frame, Player player);

    public void cleanseItem(JFrame frame, Player player) {
        if (this instanceof Relic relic && relic.isCursed()) {
            relic.setCursed(false);
            SwingRenderer.changeLabelText(frame, "The " + relic.getName() + " was cured!", LabelType.ERROR);
        } else if (this.getName().equals("Apple")) {
            player.discardItem(frame, this);
            player.addItemToInventory(frame, new PureAppleItem());
            SwingRenderer.changeLabelText(frame, "The apple was purified!", LabelType.ERROR);
        } else {
            SwingRenderer.changeLabelText(frame, "You put the " + this.getName() + " in the fountain, but nothing happened.", LabelType.ERROR);
        }
        ((PureWaterRoom) player.getCurrentRoom()).setFountainUsed(true);
        player.checkInventory(frame);
        SwingRenderer.appendMainLabelText(frame, "The fountain ran dry!", false);
    }


    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {return this.description;}
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {return this.name;}
}