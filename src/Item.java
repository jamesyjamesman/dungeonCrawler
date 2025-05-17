public abstract class Item {
    String description;
    String name;
    public Item() {
        this.description = "";
        this.name = "";
    }

    public abstract void useItem(Player player);

    public void discardItem(Player player) {
        int itemIndex = player.findItemInInventory(this);
        if (itemIndex == -1) {
            System.out.println("Error! This code should not be reachable (Item.java)");
        }
        if (player.inventory.get(itemIndex).size() == 1) {
            player.inventory.remove(itemIndex);
        } else {
            player.inventory.get(itemIndex).remove(this);
        }
    }
}