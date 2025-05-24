package main;

import main.enemy.Enemy;
import main.item.Item;
import main.item.relic.Relic;
import main.room.Room;

import java.util.ArrayList;
import static java.lang.System.exit;

public class Player {
    String name;
    int maxHealth;
    int currentHealth;
    int roomsTraversed;
    int damage;
    ArrayList<ArrayList<Item>> inventory;
    ArrayList<Relic> equippedRelics;
    int absorption;
    int inventoryCap;
    int relicCap;
    public Player(String newName) {
        this.name = newName;
        this.maxHealth = 20;
        this.currentHealth = 20;
        this.inventory = new ArrayList<>();
        this.equippedRelics = new ArrayList<>();
        this.roomsTraversed = 0;
        this.damage = 3;
        this.absorption = 0;
        this.inventoryCap = 10;
        this.relicCap = 3;
    }

    public void attack(Enemy enemy) {
        int totalDamage = this.damage;
        int damageDealt = enemy.takeDamage(totalDamage);
        if (damageDealt > 0) {
            System.out.println("The " + enemy.getSpecies() + " took " + totalDamage + " damage!");
        }
        //will get more complex with weapons, etc.
    }

    public void itemPickup(Item item) {
        if (calculateInventorySize() >= this.inventoryCap) {
            System.out.println("Your inventory is full!");
            System.out.println("Would you like to use or discard an item? (y/n)");
            String response = Main.yesOrNo();
            if (response.equals("y")) {
                Menu.inventoryLoop(this);
                if (calculateInventorySize() >= this.inventoryCap) {
                    System.out.println("Your inventory is still full... The " + item.getName() + " remains where it was.");
                    return;
                }
            } else {
                return;
            }
        }
        addItemToInventory(item);

        if (item instanceof Relic && getEquippedRelics().size() < this.relicCap) {
            System.out.println("Would you like to equip the " + item.getName() + " now? (y/n)");
            String response = Main.yesOrNo();
            if (response.equals("y")) {
                item.useItem(this);
                System.out.println("The " + item.getName() + " has been equipped!");
                return;
            }
        }
        System.out.println("You stash the " + item.getName() + " in your bag.");
    }

    public boolean addItemToInventory(Item item) {
        if (calculateInventorySize() >= this.inventoryCap) {
            System.out.println("Your inventory is full!");
            return true;
        }
        int itemIndex = findItemInInventory(item);
        if (itemIndex == -1) {
            ArrayList<Item> newItem = new ArrayList<>();
            newItem.add(item);
            this.inventory.add(newItem);
        } else {
            this.inventory.get(itemIndex).add(item);
        }
        return false;
    }

    public int calculateInventorySize() {
        int totalSize = 0;
        for (ArrayList<Item> items : this.inventory) {
            totalSize += items.size();
        }
        return totalSize;
    }

    public void discardItem(Item item) {
        int itemIndex = findItemInInventory(item);
        if (itemIndex == -1) {
            System.out.println("Error! This code should not be reachable (Item.java)");
        }
        if (this.inventory.get(itemIndex).size() == 1) {
            this.inventory.remove(itemIndex);
        } else {
            this.inventory.get(itemIndex).remove(item);
        }
    }

    //can cause index out of bounds
    public boolean checkInventory(boolean death) {

        if (this.inventory.isEmpty()) {
            if (death) {
                System.out.println("Wow! Not leaving anything for the next person...");
            } else {
                System.out.println("Your inventory is empty!");
            }
            return true;
        }

        for (int i = 0; i < this.inventory.size(); i++) {
            //Displays amount of items in parentheses (e.g. (x2)) if the amount is greater than 1
            String amount = (this.inventory.get(i).size() > 1) ? " (x" + this.inventory.get(i).size() + ")" : "";

            System.out.println((i+1) + ". " + this.inventory.get(i).getFirst().getName() +
                    amount
                    + ": " + this.inventory.get(i).getFirst().getDescription());
            System.out.println();
        }
        System.out.println("Remaining inventory space: " + (this.inventoryCap - calculateInventorySize()));
        return false;
    }

    //this is very dry, but I wasn't sure how to use checkInventory due to the different types
    public boolean checkRelics(boolean death) {

        if (this.equippedRelics.isEmpty()) {
            if (death) {
                System.out.println("Too good for those darn relics, eh?");
            } else {
                System.out.println("Your relic pouch is empty!");
            }
            return true;
        }

        for (int i = 0; i < this.equippedRelics.size(); i++) {
            System.out.println((i+1) + ". " + this.equippedRelics.get(i).getName() + ": "
                    + this.equippedRelics.get(i).getDescription());
            System.out.println();
        }
        System.out.println("Remaining relic pouch space: " + (this.relicCap - getEquippedRelics().size()));
        return false;
    }

    public int findItemInInventory(Item item) {
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).getFirst().equals(item)) {
                return i;
            }
        }
        return -1;
    }

    public int equippedRelicIndex(String relicName) {
        for (Relic equippedRelic : this.equippedRelics) {
            if (equippedRelic.getName().equals(relicName)) {
                return this.equippedRelics.indexOf(equippedRelic);
            }
        }
        return -1;
    }

    public void checkStatus() {
        System.out.println("Current player status:");
        System.out.println("Health: " + (this.currentHealth + this.absorption) + "/" + this.maxHealth);
        System.out.println("Total damage output: " + this.damage);
        System.out.println("Rooms traveled: " + this.roomsTraversed);
        System.out.println("Inventory capacity: " + this.inventoryCap);
    }
    public void takeDamage(int damage) {
        if (this.absorption > 0) {
            this.absorption -= damage;
            if (this.absorption < 0) {
                damage = -this.absorption;
                this.absorption = 0;
            } else {
                return;
            }
        }
        this.currentHealth -= damage;
        if (this.currentHealth <= 0) {
            this.currentHealth = 0;
            doDeathSequence();
        }
    }
    public int heal(int health) {
        this.currentHealth += health;
        if (this.currentHealth > this.maxHealth) {
            int healthHealed = health - (this.currentHealth - this.maxHealth);
            this.currentHealth = this.maxHealth;
            return healthHealed;
        }
        return health;
    }

    public void changeMaxHealth(int health) {
            this.maxHealth += health;
            this.currentHealth += health;
        }

    public void increaseDamage(int damageIncrease) {
        this.damage += damageIncrease;
    }

    public void useRelics(Room room) {
        for (Relic equippedRelic : this.equippedRelics) {
            equippedRelic.useRelic(this, room);
        }
    }

    public void equipRelic(Relic relic) {
        if (getEquippedRelics().size() >= this.relicCap) {
            System.out.println("You cannot equip any more relics!");
            return;
        }
        relic.setEquipped(true);
        this.equippedRelics.add(relic);
        int relicIndex = this.findItemInInventory(relic);
        this.inventory.remove(relicIndex);
        if (relic.isCursed()) {
            System.out.println("Oh no! the " + relic.getName() + " was cursed!");
        }
    }

    public void unequipRelic(Relic relic){
        if (relic.isCursed()) {
            System.out.println("The relic is welded to you painfully. You can't remove it!");
            return;
        }

        boolean inventoryFull = addItemToInventory(relic);
        if (inventoryFull) {
            System.out.println("The relic could not be unequipped!");
            return;
        }
        relic.setEquipped(false);
        System.out.println("The " + relic.getName() + " was unequipped!");
        getEquippedRelics().remove(relic);

    }

    public void doDeathSequence() {
        System.out.println("\"Ack! It's too much for me!\" " + getName() + " exclaims.");
        System.out.println(getName() + " falls to their knees... then to the ground.");
        System.out.println("GAME OVER!");
        System.out.println();
        endStatistics();
        exit(0);
    }

    // if player died from food, it will not show that item as being consumed
    public void endStatistics() {
        System.out.println("Player statistics:");
        System.out.println("Rooms completed: " + this.roomsTraversed + ".");
        System.out.println("Maximum health: " + this.maxHealth);
        System.out.println("Inventory:");
        checkInventory(true);
        System.out.println("Relics:");
        checkRelics(true);
    }

    public int getCurrentHealth() {
        return this.currentHealth;
    }
    public ArrayList<Relic> getEquippedRelics() {
        return this.equippedRelics;
    }
    public String getName() {
        return this.name;
    }
    public ArrayList<ArrayList<Item>> getInventory() {
        return this.inventory;
    }
    public void incrementRoomsTraversed() {
        this.roomsTraversed += 1;
    }
    public int getRoomsTraversed() {
        return this.roomsTraversed;
    }
    public void addAbsorption(int extraAbsorption) {
        this.absorption += extraAbsorption;
    }
    public int getInventoryCap() {
        return this.inventoryCap;
    }
    public void changeInventoryCap(int capChange) {
        this.inventoryCap += capChange;
    }
    public int getRelicCap() {
        return this.relicCap;
    }
    public void changeRelicCap(int relicCap) {
        this.relicCap += relicCap;
    }
}