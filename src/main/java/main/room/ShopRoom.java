package main.room;

import main.Main;
import main.entity.Player;
import main.initialization.ItemInit;
import main.item.Item;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.util.ArrayList;
import java.util.Random;

public class ShopRoom extends Room {
    ArrayList<Item> wares;
    ArrayList<Item> itemList;
    boolean open;
    public ShopRoom(RoomBuilder<?> builder) {
        super(builder);
        this.wares = new ArrayList<>();
        this.open = false;
        this.itemList = ItemInit.itemInit();
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        SwingRenderer.appendTextPane("Would you like to visit the shopkeeper?", true, ComponentType.PANE_MAIN);
        boolean goToShop = Main.parseResponseAsBoolean();
        if (goToShop) {
            openShop();
            renderShopUI(player);
            SwingRenderer.UIUpdater(player);
            Main.waitForResponse();
        }
        closeShop();
    }

    public void renderShopUI(Player player) {
        SwingRenderer.appendTextPane("Hello there adventurer... welcome to my shop. Buy something, if you dare...\n", true, ComponentType.PANE_MAIN);
        for (int i = 0; i < this.getWares().size(); i++) {
            SwingRenderer.addShopLabel(player, this.getWares().get(i), this);
        }
        SwingRenderer.appendTextPane("\nEnter anything to exit the shop!\n", false, ComponentType.PANE_MAIN);
    }

    public void sellItem(Item item, Player player) {
        if (player.getGold() < item.getValue()) {
            SwingRenderer.appendLabelText("You don't have enough gold!", true, ComponentType.LABEL_ERROR);
            return;
        }
        boolean inventoryFull = player.addItemToInventory(item);
        if (inventoryFull) {
            SwingRenderer.appendLabelText("Your inventory is full!", true, ComponentType.LABEL_ERROR);
            return;
        }
        player.takeGold(item.getValue());
        this.wares.remove(item);
        SwingRenderer.UIUpdater(player);
        renderShopUI(player);
    }

    public void instantiateWares() {
        int numWares = new Random().nextInt(3,7);
        ArrayList<Item> newWares = new ArrayList<>();

        int totalItemWeight = getTotalItemWeight();

        for (int i = 0; i < numWares; i++) {
            Item item = getWeightedItem(totalItemWeight);
            Item itemClone = item.clone();
            newWares.add(itemClone);
        }

        this.wares = newWares;
    }

    public int getTotalItemWeight() {
        int totalWeight = 0;
        for (Item item : this.itemList) {
            totalWeight += item.getShopWeight();
        }
        return totalWeight;
    }

    public Item getWeightedItem(int totalWeight) {
        int randomWeight = new Random().nextInt(totalWeight);
        for (Item item : this.itemList) {
            int weight = item.getShopWeight();
            randomWeight -= weight;
            if (randomWeight < 0) {
                return item;
            }
        }
        System.out.println("This code should be inaccessible!");
        return null;
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

    public ArrayList<Item> getWares() {
        return this.wares;
    }
}
