package main.room;

import main.Main;
import main.Player;
import main.item.Item;
import main.item.ItemBlueprint;
import main.item.ItemID;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class ShopRoom extends Room {
    ArrayList<Item> wares;
    ArrayList<ArrayList<ItemBlueprint>> shopOptions;
    boolean open;
    public ShopRoom() {
        this.wares = new ArrayList<>();
        this.active = false;
        this.open = true;
        this.appearance = "You can hear a bell ringing. It's inviting?";
        this.description = "You see a cold glow from a small opening in the wall, and approach it.";
        this.id = 8394;
        this.type = RoomType.SPECIAL;
        this.roomsRequired = 20;
        this.numExits = 2;
        this.selectionWeight = 3;
        this.shopOptions = new ArrayList<>();
        this.shopOptions.add(new ArrayList<>());
        this.shopOptions.getFirst().add(new ItemBlueprint(1.0, ItemID.BUFF_DAMAGE));
        this.shopOptions.getFirst().add(new ItemBlueprint(1.0, ItemID.HEALTH_CHOCOLATE));
    }

    //implement shop that covers main textbox.
    @Override
    public void completeRoomActions(Player player, JFrame frame) {
        super.completeRoomActions(player, frame);
        SwingRenderer.appendMainLabelText(frame, "Would you like to bug the shopkeeper?", true);
        boolean goToShop = Main.parseResponseAsBoolean(frame);
        if (goToShop) {
            openShop();
            renderShopUI(frame, player);
            Main.waitForResponse(frame);
        }
        closeShop();
    }

    public void renderShopUI(JFrame frame, Player player) {
        SwingRenderer.appendMainLabelText(frame, "Hello there adventurer... welcome to my shop. Buy something, if you dare...\n", true);
        for (int i = 0; i < this.getWares().size(); i++) {
            SwingRenderer.addShopLabel(frame, player, this.getWares().get(i), this);
        }
        SwingRenderer.appendMainLabelText(frame, "\nEnter anything to exit the shop!\n", false);
    }

    public void sellItem(JFrame frame, Item item, Player player) {
        if (player.getGold() < item.getValue()) {
            SwingRenderer.changeLabelText(frame, "You don't have enough gold!", ComponentType.LABEL_ERROR);
            return;
        }
        boolean inventoryFull = player.addItemToInventory(frame, item);
        if (inventoryFull) {
            SwingRenderer.changeLabelText(frame, "Your inventory is full!", ComponentType.LABEL_ERROR);
            return;
        }
        player.takeGold(item.getValue());
        this.wares.remove(item);
        SwingRenderer.UIUpdater(frame, player);
        renderShopUI(frame, player);
    }

    public void closeShop() {
        this.open = false;
    }
    public void openShop() {
        this.open = true;
        instantiateWares();
    }
    public boolean isOpen() {
        return this.open;
    }
    public void instantiateWares() {
        int whichWares = new Random().nextInt(this.shopOptions.size());
        ArrayList<ItemBlueprint> newWareOptions = this.shopOptions.get(whichWares);
        ArrayList<Item> newWares = new ArrayList<>();

        for (ItemBlueprint newWareOption : newWareOptions) {
            newWares.add(Item.itemFactory(newWareOption.getID()));
        }

        this.wares = newWares;
    }
    public ArrayList<Item> getWares() {
        return this.wares;
    }
}
