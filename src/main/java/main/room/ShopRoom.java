package main.room;

import main.entity.Player;
import main.initialization.ItemInit;
import main.item.Item;

import java.util.ArrayList;
import java.util.Random;

public class ShopRoom extends Room {
    private ArrayList<Item> wares;
    private final ArrayList<Item> itemList;
    private boolean open;
    public ShopRoom(RoomBuilder<?> builder) {
        super(builder);
        this.wares = new ArrayList<>();
        this.open = false;
        this.itemList = ItemInit.itemInit();
    }

    @Override
    public void completeRoomActions(Player player) {
        super.completeRoomActions(player);
        openShop();
    }

    public boolean sellItem(Item item, Player player) {
        if (player.getGold() < item.getValue()) {
            return false;
        }
        boolean inventoryFull = player.addItemToInventory(item);
        if (inventoryFull) {
            return false;
        }
        player.takeGold(item.getValue());
        this.wares.remove(item);
        return true;
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
    public boolean getOpen() {
        return this.open;
    }

    public ArrayList<Item> getWares() {
        return this.wares;
    }
}
