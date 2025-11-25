package main.entity;

import main.App;
import main.Statuses;
import main.entity.enemy.Enemy;
import main.item.Item;
import main.item.Loot;
import main.item.relic.DamageRelic;
import main.item.relic.Relic;
import main.item.relic.RelicID;
import main.item.weapon.Weapon;
import main.room.Room;
import main.room.ShopRoom;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

//potentially make a "stats" object for player -- would cut this in about half
public class Player extends Entity {
    private int roomsTraversed;
    private final ArrayList<ArrayList<Item>> inventory;
    private final ArrayList<Relic> equippedRelics;
    private int absorption;
    private int inventoryCap;
    private int relicCap;
    private int level;
    private int experience;
    private int expToNextLevel;
    private Room currentRoom;
    private final Statuses currentStatuses;
    private Weapon equippedWeapon;
    private int gold;
    public Player() {
        super(Species.PLAYER, 20, 3);
        this.inventory = new ArrayList<>();
        this.equippedRelics = new ArrayList<>();
        this.roomsTraversed = 0;
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
        int totalDamage = calculateWeakenedAttack();
        enemy.takeDamage(totalDamage);
    }

    public int calculateTotalAttack() {
        int weaponDamage = (this.equippedWeapon != null) ? this.equippedWeapon.getDamage() : 0;
        int damageRelicIndex = equippedRelicIndex(RelicID.DAMAGE);
        int relicDamage = (damageRelicIndex != -1) ? ((DamageRelic) getEquippedRelics().get(damageRelicIndex)).getDamage() : 0;
        return getDamage() + weaponDamage + relicDamage;
    }

    public int calculateWeakenedAttack() {
        return weakenAttack(calculateTotalAttack());
    }

    public void itemPickup(Item item) {
        if (calculateInventorySize() >= this.inventoryCap) {
            if (calculateInventorySize() >= this.inventoryCap) {
                // relic is removed from unused relics as soon as it is generated, so it has to be put back
                if (item instanceof Relic relic && relic.isFindable()) {
                    App.INSTANCE.addUnusedRelic(relic);
                }
                return;
            }
        }
        addItemToInventory(item);

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

    public boolean sellItem(Item item) {
        if (item instanceof Relic relic && relic.isCursed()) {
            return false;
        } else {
            discardItem(item);
            this.addGold(item.getValue());
            return true;
        }
    }

    public void discardItem(Item item) {
        int itemIndex = findItemInInventory(item);
        if (itemIndex == -1) {
            System.out.println("Error! This code should not be reachable (Item.java)");
        }
        if (item instanceof Relic relic && relic.isFindable()) {
            App.INSTANCE.addUnusedRelic(relic);
        }
        if (this.inventory.get(itemIndex).size() == 1) {
            this.inventory.remove(itemIndex);
        } else {
            this.inventory.get(itemIndex).remove(item);
        }
    }

    //can cause index out of bounds
    public void checkInventory() {

        for (ArrayList<Item> items : this.inventory) {
            Item item = items.getFirst();
            //Displays amount of items in parentheses (e.g. (x2)) if the amount is greater than 1
            String amount = (items.size() > 1) ? " (x" + items.size() + ")" : "";

            //cinema
            String output = item.getName() + ((this.getCurrentRoom() instanceof ShopRoom shopRoom && shopRoom.getOpen()) ? " [" + item.getValue() + "G]" : "") + amount + ": " + item.getDescription();
            output = output.concat("\n");
            Color color;
            if (item instanceof Relic relic && relic.isCursed() && equippedRelicIndex(RelicID.CURSE_DETECTION) != -1) {
                color = new Color(130, 30, 190);
            } else {
                color = Color.white;
            }
        }
    }

    //this is very dry, but I wasn't sure how to use checkInventory due to the different types
    public void checkRelics() {
        Color color;

        for (Relic relic : this.equippedRelics) {
            String output = relic.getName() + ": " + relic.getDescription();
            output += "\n";
            if (relic.isCursed()) {
                color = new Color(130, 30, 190);
            } else {
                color = Color.white;
            }
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
            if (equippedRelic.getRelicType() == relicName) {
                return this.equippedRelics.indexOf(equippedRelic);
            }
        }
        return -1;
    }

    public boolean hasRelic(Relic relic) {
        return equippedRelicIndex(relic.getRelicType()) != -1 || findItemInInventoryByName(relic) != -1;
    }

    //should be called any time anything printed can change, e.g. health change, absorption change, etc.
    public void checkStatus() {
        String output = "";
    // Renders level and exp to next level, unless the player is at the max level (10), where it just shows the level.
        output = output.concat("Level " + this.level + (this.level < 10 ? " (" + this.experience + "/" + this.expToNextLevel + " exp)" : ""));
        output = output.concat("\n");
        output = output.concat("Gold: " + this.gold + "\n");
        output = output.concat("Health: " + getCurrentHealth() + ((this.absorption > 0) ? " (+" + this.absorption + ")" : "") + " / " + getMaxHealth() + "\n");
        output = output.concat("Attack damage: " + calculateTotalAttack() + "\n");
        output = output.concat("Rooms traveled: " + this.roomsTraversed + "\n");
        output = output.concat("Inventory: " + calculateInventorySize() + "/" + this.inventoryCap + "\n");
        output = output.concat("Relics: " + this.equippedRelics.size() + "/" + this.relicCap + "\n");
        output = output.concat(statusEffectChecker());
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

    @Override
    public int takeDamage(int damage) {
        if (this.absorption > 0) {
            this.absorption -= damage;
            if (this.absorption < 0) {
                damage = -1 * this.absorption;
                this.absorption = 0;
            } else {
                checkStatus();
                return damage;
            }
        }
        damage = super.takeDamage(damage);
        checkStatus();
        return damage;
    }

    public void useRelics() {
        for (Relic equippedRelic : this.equippedRelics) {
            equippedRelic.useRelic(this);
        }
    }

    public boolean equipRelic(Relic relic) {
        if (getEquippedRelics().size() >= this.relicCap) {
            return false;
        }
        this.equippedRelics.add(relic);
        int relicIndex = this.findItemInInventory(relic);
        this.inventory.remove(relicIndex);
        if (relic.isCursed()) {
            this.currentStatuses.addCursed(1);
        }
        checkStatus();
        return true;
    }

    public boolean unequipRelic(Relic relic){
        if (relic.isCursed() || addItemToInventory(relic)) {
            return false;
        }
        getEquippedRelics().remove(relic);
        return true;
    }

    @Override
    public void die() {
        checkStatus();
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // returns 2 newlines at the end of the output string. should probably have 0-1 at the end
    public String levelUp() {
        String output = "";
        while (this.experience >= this.expToNextLevel && this.level < 10) {
            output += "You leveled up!\n";
            this.experience -= this.expToNextLevel;
            this.expToNextLevel = (int) Math.round(this.expToNextLevel * 1.2);
            this.level += 1;
            output = levelUpEffects(this.level, output);
        }
        return output;
    }

    public ArrayList<Item> collectLoot(Loot loot) {
        ArrayList<Item> droppedItems = new ArrayList<>();
        addGold(loot.getGold());
        for (int i = 0; i < loot.getItems().size(); i++) {
            Item item = loot.getItems().get(i);
            if (item instanceof Relic relic && this.hasRelic(relic)) {
                int halfBackGuarantee = relic.getValue() / 2;
                addGold(halfBackGuarantee);
                continue;
            }
            if (new Random().nextDouble(0, 1) > item.getDropChance()) {
                continue;
            }
            Item itemClone = item.clone();
            droppedItems.add(itemClone);
            itemPickup(itemClone);
        }
        return droppedItems;
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
            increaseMaxHealth(maxHealthChange);
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
        doFireDamage();
        if (inBattle) {
            return;
        }
        doCurseDamage();
    }

    //todo: consider moving these to the statuses class

    public int weakenAttack(int initialDamage) {
        int weaknessLevel = this.currentStatuses.getWeakened();
        if (weaknessLevel == 0)
            return initialDamage;
        int finalDamage = initialDamage / (weaknessLevel + 1);
    // 1 - (1/(n+1)) chance to decrease weakness level by one
        if (new Random().nextInt(weaknessLevel + 1) != 0) {
            this.currentStatuses.addWeakened(-1);
        }
        return finalDamage;
    }

    public void doFireDamage() {
        int fireLevel = this.currentStatuses.getFire();
        if (fireLevel == 0) {
            return;
        }
        takeDamage(1);
        this.currentStatuses.setFire(fireLevel - 1);
    }

    public void doPoisonDamage() {
        int poisonLevel = this.currentStatuses.getPoison();
        if (poisonLevel == 0) {
            return;
        }
        takeDamage(poisonLevel);
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
                }
            }
        }

    }

    public ArrayList<Relic> getEquippedRelics() {
        return this.equippedRelics;
    }
    public String getName() {
        return "you";
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
    public void setInventoryCap(int inventoryCap) {
        this.inventoryCap = inventoryCap;
    }
    public void changeInventoryCap(int capChange) {
        this.inventoryCap += capChange;
    }
    public int getRelicCap() {
        return this.relicCap;
    }
    public void setRelicCap(int relicCap) {
        this.relicCap = relicCap;
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
    public int getLevel() {
        return this.level;
    }
    public int getExperience() {
        return this.experience;
    }
    public int getExpToNextLevel() {
        return this.expToNextLevel;
    }
}
