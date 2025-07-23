package main.item;

import main.Player;
import main.item.relic.Relic;
import main.room.PureWaterRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import javax.swing.*;

public abstract class Item {
    String description;
    String name;
    public Item() {
        this.description = "";
        this.name = "";
    }

    public void useItem(JFrame frame, Player player) {
       UIUpdater(frame, player);
    }

    public void UIUpdater(JFrame frame, Player player) {
        player.checkInventory(frame);
        player.checkRelics(frame);
        player.checkStatus(frame);
        SwingRenderer.setInputFocus(frame);
    }

    public void cleanseItem(JFrame frame, Player player) {
        if (this instanceof Relic relic && relic.isCursed()) {
            relic.setCursed(false);
            SwingRenderer.changeLabelText(frame, "The " + relic.getName() + " was cured!", ComponentType.LABEL_ERROR);
        } else if (this.getName().equals("Apple")) {
            player.discardItem(frame, this);
            player.addItemToInventory(frame, new PureAppleItem());
            SwingRenderer.changeLabelText(frame, "The apple was purified!", ComponentType.LABEL_ERROR);
        } else {
            SwingRenderer.changeLabelText(frame, "You put the " + this.getName() + " in the fountain, but nothing happened.", ComponentType.LABEL_ERROR);
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