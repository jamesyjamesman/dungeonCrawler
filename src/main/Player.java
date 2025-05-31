package main;

import main.enemy.Enemy;
import main.item.Item;
import main.item.relic.Relic;
import main.room.Room;
import main.swing.LabelType;
import main.swing.SwingRenderer;

import javax.swing.*;
import java.util.ArrayList;

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
    int level;
    int experience;
    int expToNextLevel;
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
        this.level = 1;
        this.experience = 0;
        this.expToNextLevel = 10;
    }

    public void attack(JFrame frame, Enemy enemy) {
        int totalDamage = this.damage;
        int damageDealt = enemy.takeDamage(frame, totalDamage);
        if (damageDealt > 0) {
            SwingRenderer.appendMainLabelText(frame, "The " + enemy.getSpecies() + " took " + totalDamage + " damage!");
        }
        //will get more complex with weapons, etc.
    }

    public void itemPickup(JFrame frame, Item item) {
        if (calculateInventorySize() >= this.inventoryCap) {
            SwingRenderer.appendMainLabelText(frame, "Your inventory is full!\nEnter anything to continue, if your inventory is still full the item will be lost.");
            Main.yesOrNo(frame);
            if (calculateInventorySize() >= this.inventoryCap) {
                SwingRenderer.appendMainLabelText(frame, "Your inventory is still full... The " + item.getName() + " remains where it was.");
                return;
            }
        }
        addItemToInventory(item);

        if (item instanceof Relic && getEquippedRelics().size() < this.relicCap) {
            SwingRenderer.changeLabelText(frame, "Would you like to equip the " + item.getName() + " now? (y/n)", LabelType.MAIN);
            String response = Main.yesOrNo(frame);
            if (response.equals("y")) {
                item.useItem(frame, this);
                SwingRenderer.appendMainLabelText(frame, "The " + item.getName() + " has been equipped!");
                checkRelics(frame, false);
                return;
            }
        }
        SwingRenderer.appendMainLabelText(frame, "You stash the " + item.getName() + " in your bag.");
    }

    public boolean addItemToInventory(Item item) {
        if (calculateInventorySize() >= this.inventoryCap) {
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
    public void checkInventory(JFrame frame, boolean death) {
        SwingRenderer.clearInventoryPane(frame, 1);
        if (this.inventory.isEmpty()) {
            //deprecated ?
            if (death) {
                System.out.println(Main.colorString("Wow! Not leaving anything for the next person...", DialogueType.INVENTORY));
            } else {
                System.out.println(Main.colorString("Your inventory is empty!", DialogueType.INVENTORY));
            }
            return;
        }
        for (int i = 0; i < this.inventory.size(); i++) {
            //Displays amount of items in parentheses (e.g. (x2)) if the amount is greater than 1
            String amount = (this.inventory.get(i).size() > 1) ? " (x" + this.inventory.get(i).size() + ")" : "";

            String output = (i+1) + ". " + this.inventory.get(i).getFirst().getName() +
                    amount
                    + ": " + this.inventory.get(i).getFirst().getDescription();
            output = output.concat("\n");
            //could make this create a JButton (title) and a JLabel (description), as JLabels wrap
            SwingRenderer.addInventoryButton(frame, output, this, i, 1);
        }
    }

    //this is very dry, but I wasn't sure how to use checkInventory due to the different types
    public void checkRelics(JFrame frame, boolean death) {
        SwingRenderer.clearInventoryPane(frame, 3);
        if (this.equippedRelics.isEmpty()) {
            //deprecated?
            if (death) {
                System.out.println(Main.colorString("Too good for those darn relics, eh?", DialogueType.INVENTORY));
            } else {
                System.out.println(Main.colorString("Your relic pouch is empty!", DialogueType.INVENTORY));
            }
            return;
        }

        for (int i = 0; i < this.equippedRelics.size(); i++) {
            String output = (i+1) + ". " + this.equippedRelics.get(i).getName() + ": "
                    + this.equippedRelics.get(i).getDescription();
            output = output.concat("\n\n");
            SwingRenderer.addInventoryButton(frame, output, this, i, 3);
        }
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

    //should be called any time anything printed can change, e.g. health change, absorption change, etc.
    public void checkStatus(JFrame frame) {
        String output = "";
        output = output.concat("Current player status:\n");
        output = output.concat("Level " + this.level + (this.level < 10 ? " (" + this.experience + "/" + this.expToNextLevel + " exp)" : ""));
        output = output.concat("\n");
        output = output.concat("Health: " + (this.currentHealth + this.absorption) + "/" + this.maxHealth + "\n");
        output = output.concat("Attack damage: " + this.damage + "\n");
        output = output.concat("Rooms traveled: " + this.roomsTraversed + "\n");
        output = output.concat("Inventory capacity: " + this.inventoryCap + "\n");
        SwingRenderer.changeLabelText(frame, output, LabelType.STATUS);
    }

    public void takeDamage(JFrame frame, int damage) {
        if (this.absorption > 0) {
            this.absorption -= damage;
            if (this.absorption < 0) {
                damage = -1 * this.absorption;
                this.absorption = 0;
            } else {
                checkStatus(frame);
                return;
            }
        }
        this.currentHealth -= damage;
        if (this.currentHealth <= 0) {
            this.currentHealth = 0;
            doDeathSequence(frame);
        }
        checkStatus(frame);
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

    public void useRelics(JFrame frame, Room room) {
        for (Relic equippedRelic : this.equippedRelics) {
            equippedRelic.useRelic(frame, this, room);
        }
    }

    public boolean equipRelic(JFrame frame, Relic relic) {
        if (getEquippedRelics().size() >= this.relicCap) {
            SwingRenderer.changeLabelText(frame, "You cannot equip any more relics!", LabelType.ERROR);
            return false;
        }
        relic.setEquipped(true);
        this.equippedRelics.add(relic);
        int relicIndex = this.findItemInInventory(relic);
        this.inventory.remove(relicIndex);
        if (relic.isCursed()) {
            SwingRenderer.changeLabelText(frame, "Oh no! the " + relic.getName() + " was cursed!", LabelType.ERROR);
        }
        return true;
    }

    public boolean unequipRelic(JFrame frame, Relic relic){
        if (relic.isCursed()) {
            SwingRenderer.changeLabelText(frame, "The relic is welded to you painfully. You can't remove it!", LabelType.ERROR);
            return false;
        }

        boolean inventoryFull = addItemToInventory(relic);
        if (inventoryFull) {
            SwingRenderer.changeLabelText(frame, "Your inventory is full; the relic could not be unequipped!", LabelType.ERROR);
            return false;
        }
        relic.setEquipped(false);
        SwingRenderer.changeLabelText(frame, "The " + relic.getName() + " was unequipped!", LabelType.ERROR);
        getEquippedRelics().remove(relic);
        return true;
    }

    public void doDeathSequence(JFrame frame) {
        SwingRenderer.changeLabelText(frame, "\"Ack! It's too much for me!\" " + getName() + " exclaims.\n" + getName() + " falls to their knees... then to the ground.\n" + "GAME OVER!", LabelType.MAIN);
//        endStatistics(frame);
        while (true) {
        }
    }

    public void checkLevelUp(JFrame frame) {
        if (this.experience >= this.expToNextLevel && this.level < 10) {
            levelUp(frame);
        }
    }

    //could make a popup-type thing?
    public void levelUp(JFrame frame) {
        this.experience -= this.expToNextLevel;
        this.expToNextLevel = (int) Math.round(this.expToNextLevel * 1.2);
        this.level += 1;
        SwingRenderer.appendMainLabelText(frame, "You leveled up!");
        levelUpEffects(frame, this.level);
        checkLevelUp(frame);
    }

    public void levelUpEffects(JFrame frame, int newLevel) {
        int maxHealthChange = 0;
        int inventoryCapChange = 0;
        int damageChange = 0;
        int relicCapChange = 0;
        switch (newLevel) {
            case 2:
                maxHealthChange = 1;
                break;
            case 3:
                maxHealthChange = 2;
                inventoryCapChange = 1;
                break;
            case 4, 7:
                damageChange = 1;
                break;
            case 5:
                maxHealthChange = 2;
                relicCapChange = 1;
                break;
            case 6:
                maxHealthChange = 3;
                inventoryCapChange = 2;
                break;
            case 8:
                inventoryCapChange = 2;
                relicCapChange = 1;
                break;
            case 9:
                maxHealthChange = 4;
                break;
            case 10:
                maxHealthChange = 5;
                damageChange = 2;
                relicCapChange = 2;
                inventoryCapChange = 3;
                this.expToNextLevel = 100000000;
                SwingRenderer.appendMainLabelText(frame, "You're at the maximum level!");
                break;
        }
        if (maxHealthChange != 0) {
            SwingRenderer.appendMainLabelText(frame, "Your maximum health increased by " + maxHealthChange + "!");
            changeMaxHealth(maxHealthChange);
        }
        if (inventoryCapChange != 0) {
            SwingRenderer.appendMainLabelText(frame, "Your inventory size increased by " + inventoryCapChange + "!");
            changeInventoryCap(inventoryCapChange);
        }
        if (damageChange != 0) {
            SwingRenderer.appendMainLabelText(frame, "Your attack damage increased by " + damageChange + "!");
            increaseDamage(damageChange);
        }
        if (relicCapChange != 0) {
            SwingRenderer.appendMainLabelText(frame, "Your relic pouch capacity increased by " + relicCapChange + "!");
            changeRelicCap(relicCapChange);
        }
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
    public int getAbsorption() {
        return this.absorption;
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
    public void changeExperience(int addedExp) {
        this.experience += addedExp;
    }
}
