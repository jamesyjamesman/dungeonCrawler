package main;

import main.enemy.Enemy;
import main.item.Item;
import main.item.relic.Relic;
import main.item.relic.RelicID;
import main.item.weapon.Weapon;
import main.room.Room;
import main.room.ShopRoom;
import main.swing.ComponentType;
import main.swing.SwingRenderer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

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
    Room currentRoom;
    Statuses currentStatuses;
    Weapon equippedWeapon;
    int gold;
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
        this.currentRoom = null;
        this.currentStatuses = new Statuses();
        this.equippedWeapon = null;
        this.gold = 0;
    }
    public void attack(Enemy enemy) {
        int totalDamage = weakenAttack(calculateTotalAttack());
        int damageDealt = enemy.takeDamage(totalDamage);
        if (damageDealt > 0) {
            SwingRenderer.appendMainLabelText("The " + enemy.getSpecies() + " took " + totalDamage + " damage!\n", false);
        }
    }

    public int calculateTotalAttack() {
        return this.damage + ((this.equippedWeapon != null) ? this.equippedWeapon.getDamage() : 0);
    }

    public void itemPickup(Item item) {
        if (calculateInventorySize() >= this.inventoryCap) {
            SwingRenderer.appendMainLabelText("Your inventory is full!\nEnter anything to continue, if your inventory is still full the item will be lost.", false);
            Main.waitForResponse();
            if (calculateInventorySize() >= this.inventoryCap) {
                SwingRenderer.appendMainLabelText("Your inventory is still full... The " + item.getName() + " remains where it was.", false);
                return;
            }
        }
        addItemToInventory(item);

        if (item instanceof Relic && getEquippedRelics().size() < this.relicCap) {
            SwingRenderer.appendMainLabelText("Would you like to equip the " + item.getName() + " now? (y/n)", true);
            boolean wantsEquipped = Main.parseResponseAsBoolean();
            if (wantsEquipped) {
                item.useItem(this);
                SwingRenderer.appendMainLabelText("The " + item.getName() + " has been equipped!", false);
                checkRelics();
                return;
            }
        }
        SwingRenderer.appendMainLabelText("You stash the " + item.getName() + " in your bag.\n", false);
    }

    public boolean addItemToInventory(Item item) {
        if (calculateInventorySize() >= this.inventoryCap) {
            return true;
        }
        int itemIndex = findItemInInventoryByName(item);
        if (itemIndex == -1 || !item.isStackable()) {
            ArrayList<Item> newItem = new ArrayList<>();
            newItem.add(item);
            this.inventory.add(newItem);
        } else {
            this.inventory.get(itemIndex).add(item);
        }
        checkStatus();
        return false;
    }

    //Finds by name versus direct object comparison, would be better with an enumerator
    public int findItemInInventoryByName(Item item) {
        String itemName = item.getName();
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).getFirst().getName().equals(itemName)) {
                return i;
            }
        }
        return -1;
    }

    public int calculateInventorySize() {
        int totalSize = 0;
        for (ArrayList<Item> items : this.inventory) {
            totalSize += items.size();
        }
        return totalSize;
    }

    public void sellItem(Item item) {
        discardItem(item);
        this.addGold(item.getValue());
        SwingRenderer.UIUpdater(this);
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
        SwingRenderer.UIUpdater(this);
    }

    //can cause index out of bounds
    public void checkInventory() {
        SwingRenderer.clearInventoryPane(false);

        for (ArrayList<Item> items : this.inventory) {
            Item item = items.getFirst();
            //Displays amount of items in parentheses (e.g. (x2)) if the amount is greater than 1
            String amount = (items.size() > 1) ? " (x" + items.size() + ")" : "";

            String output = item.getName() + ((this.getCurrentRoom() instanceof ShopRoom shopRoom && shopRoom.isOpen()) ? " [" + item.getValue() + "G]" : "") + amount + ": " + item.getDescription();
            output = output.concat("\n");
            Color color;
            if (item instanceof Relic relic && relic.isCursed() && equippedRelicIndex(RelicID.CURSE_DETECTION) != -1) {
                color = new Color(130, 30, 190);
            } else {
                color = Color.white;
            }
            SwingRenderer.addItemLabel(output, this, item, color);
        }
    }

    //this is very dry, but I wasn't sure how to use checkInventory due to the different types
    public void checkRelics() {
        SwingRenderer.clearInventoryPane(true);
        Color color;

        for (Relic relic : this.equippedRelics) {
            String output = relic.getName() + ": " + relic.getDescription();
            output += "\n";
            if (relic.isCursed()) {
                color = new Color(130, 30, 190);
            } else {
                color = Color.white;
            }
            SwingRenderer.addItemLabel(output, this, relic, color);
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

    public int equippedRelicIndex(RelicID relicName) {
        for (Relic equippedRelic : this.equippedRelics) {
            if (equippedRelic.getType() == relicName) {
                return this.equippedRelics.indexOf(equippedRelic);
            }
        }
        return -1;
    }

    //should be called any time anything printed can change, e.g. health change, absorption change, etc.
    public void checkStatus() {
        String output = "";
    // Renders level and exp to next level, unless the player is at the max level (10), where it just shows the level.
        output = output.concat("Level " + this.level + (this.level < 10 ? " (" + this.experience + "/" + this.expToNextLevel + " exp)" : ""));
        output = output.concat("\n");
        output = output.concat("Gold: " + this.gold + "\n");
        output = output.concat("Health: " + (this.currentHealth + this.absorption) + "/" + this.maxHealth + "\n");
        output = output.concat("Attack damage: " + calculateTotalAttack() + "\n");
        output = output.concat("Rooms traveled: " + this.roomsTraversed + "\n");
        output = output.concat("Inventory: " + calculateInventorySize() + "/" + this.inventoryCap + "\n");
        output = output.concat("Relics: " + this.equippedRelics.size() + "/" + this.relicCap + "\n");
        output = output.concat(statusEffectChecker());
        SwingRenderer.changeLabelText(output, ComponentType.LABEL_STATUS);
    }

    public String statusEffectChecker() {
        boolean anyEffects = false;
        String output = "Status effects: \n";
        if (this.currentStatuses.getCursed() > 0) {
            output += "Cursed: Level " + this.currentStatuses.getCursed() + "\n";
            anyEffects = true;
        }
        if (this.currentStatuses.getPoison() > 0) {
            output += "Poison: Level " + this.currentStatuses.getPoison() + "\n";
            anyEffects = true;
        }
        if (this.currentStatuses.getWeakened() > 0) {
            output += "Weakened: Level " + this.currentStatuses.getWeakened() + "\n";
            anyEffects = true;
        }
        if (this.currentStatuses.getFire() > 0) {
            output += "Burning: Level " + this.currentStatuses.getFire() + "\n";
            anyEffects = true;
        }
        if (anyEffects) {
            return output;
        }
        return "";
    }

    public void takeDamage(int damage) {
        if (this.absorption > 0) {
            this.absorption -= damage;
            if (this.absorption < 0) {
                damage = -1 * this.absorption;
                this.absorption = 0;
            } else {
                checkStatus();
                return;
            }
        }
        this.currentHealth -= damage;
        if (this.currentHealth <= 0) {
            this.currentHealth = 0;
            doDeathSequence();
        }
        checkStatus();
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

    public boolean equipRelic(Relic relic) {
        if (getEquippedRelics().size() >= this.relicCap) {
            SwingRenderer.changeLabelText("You cannot equip any more relics!", ComponentType.LABEL_ERROR);
            return false;
        }
        relic.setEquipped(true);
        this.equippedRelics.add(relic);
        int relicIndex = this.findItemInInventory(relic);
        this.inventory.remove(relicIndex);
        if (relic.isCursed()) {
            SwingRenderer.changeLabelText("Oh no! the " + relic.getName() + " was cursed!", ComponentType.LABEL_ERROR);
            this.currentStatuses.setCursed(this.currentStatuses.getCursed() + 1);
        }
        checkStatus();
        return true;
    }

    public boolean unequipRelic(Relic relic){
        if (relic.isCursed()) {
            SwingRenderer.changeLabelText("The relic is welded to you painfully. You can't remove it!", ComponentType.LABEL_ERROR);
            return false;
        }

        boolean inventoryFull = addItemToInventory(relic);
        if (inventoryFull) {
            SwingRenderer.changeLabelText("Your inventory is full; the relic could not be unequipped!", ComponentType.LABEL_ERROR);
            return false;
        }
        relic.setEquipped(false);
        SwingRenderer.changeLabelText("The " + relic.getName() + " was unequipped!", ComponentType.LABEL_ERROR);
        getEquippedRelics().remove(relic);
        return true;
    }

    public void doDeathSequence() {
        checkStatus();
        SwingRenderer.appendMainLabelText("\"Ack! It's too much for me!\" " + getName() + " exclaims.\n" + getName() + " falls to their knees... then to the ground.\n" + "GAME OVER!", true);
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void levelUp() {
        String output = "";
        while (this.experience >= this.expToNextLevel && this.level < 10) {
            output += "You leveled up!\n";
            this.experience -= this.expToNextLevel;
            this.expToNextLevel = (int) Math.round(this.expToNextLevel * 1.2);
            this.level += 1;
            output = levelUpEffects(this.level, output);
        }
        if (!output.isEmpty()) {
            SwingRenderer.createPopup(output);
        }
    }

    public String levelUpEffects(int newLevel, String output) {
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
                output +=  "You're at the maximum level!\n";
                break;
        }
        if (maxHealthChange != 0) {
            output += "Your maximum health increased by " + maxHealthChange + "!\n";
            changeMaxHealth(maxHealthChange);
        }
        if (inventoryCapChange != 0) {
            output += "Your inventory size increased by " + inventoryCapChange + "!\n";
            changeInventoryCap(inventoryCapChange);
        }
        if (damageChange != 0) {
            output += "Your attack damage increased by " + damageChange + "!\n";
            increaseDamage(damageChange);
        }
        if (relicCapChange != 0) {
            output += "Your relic pouch capacity increased by " + relicCapChange + "!\n";
            changeRelicCap(relicCapChange);
        }
        return output + "\n";
    }
    public void statusHandler(boolean inBattle) {
        doPoisonDamage();
        if (inBattle) {
            return;
        }
        doCurseDamage();
    }

    public int weakenAttack(int initialDamage) {
        int weaknessLevel = this.currentStatuses.getWeakened();
        if (weaknessLevel == 0) {return initialDamage;}
        int finalDamage = initialDamage / (weaknessLevel + 1);
    // 1 - (1/(n+1)) chance to decrease weakness level by one
        if (new Random().nextInt(weaknessLevel + 1) != 0) {
            this.currentStatuses.setWeakened(this.currentStatuses.getWeakened() - 1);
            SwingRenderer.addHealthText("Your weakness level decreased by one!");
        }
        return finalDamage;
    }

    public void doPoisonDamage() {
        int poisonLevel = this.currentStatuses.getPoison();
        if (poisonLevel == 0) {
            return;
        }
        takeDamage(poisonLevel);
        SwingRenderer.addHealthText("You took " + poisonLevel + " poison damage! Your poison level is now " + (poisonLevel - 1) + ".");
        this.currentStatuses.setPoison(poisonLevel - 1);
    }

    public void doCurseDamage() {
        int curseLevel = this.currentStatuses.getCursed();
        if (curseLevel == 0) {
            return;
        }
        if (equippedRelicIndex(RelicID.CURSE_HEAL) == -1) {
            int totalDamage = 0;
            for (int i = 0; i < curseLevel; i++) {
                totalDamage += new Random().nextInt(4);
            }
            if (totalDamage > 0) {
                SwingRenderer.addHealthText("You took " + totalDamage + " damage from your cursed relics!");
                takeDamage(totalDamage);
            }
        } else {
            int totalHeal = 0;
            for (int i = 0; i < curseLevel; i++) {
                totalHeal += new Random().nextInt(2);
            }
            if (totalHeal > 0) {
                int amountHealed = heal(totalHeal);
                if (amountHealed > 0) {
                    SwingRenderer.addHealthText("You gained " + amountHealed + " health from your cursed relics!");
                }
            }
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
    public void setRoomsTraversed(int roomsTraversed) {
        this.roomsTraversed = roomsTraversed;
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
    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }
    public Room getCurrentRoom() {
        return this.currentRoom;
    }
    public Statuses getCurrentStatuses() {
        return this.currentStatuses;
    }
    public void setEquippedWeapon(Weapon weapon) {
        if (this.equippedWeapon != null && weapon != null) {
            SwingRenderer.changeLabelText("You cannot equip more than one weapon!", ComponentType.LABEL_ERROR);
            return;
        }
        this.equippedWeapon = weapon;
    }
    public Weapon getEquippedWeapon() {
        return this.equippedWeapon;
    }
    public int getGold() {
        return this.gold;
    }
    public void addGold(int gold) {
        this.gold += gold;
    }
    // Does not handle attempted pulls greater than balance
    public void takeGold(int cost) {
            this.gold -= cost;
    }
}
