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
    public Player(String newName) {
        this.name = newName;
        this.maxHealth = 20;
        this.currentHealth = 20;
        this.inventory = new ArrayList<>();
        this.equippedRelics = new ArrayList<>();
        this.roomsTraversed = 0;
        this.damage = 3;
    }

    public boolean attack(Enemy enemy) {
        int totalDamage = this.damage;
        System.out.println("The " + enemy.getSpecies() + " took " + totalDamage + " damage!");
        return enemy.takeDamage(totalDamage);

        //will get more complex with weapons, etc.
    }

    public void addItemToInventory(Item item) {
        int itemIndex = findItemInInventory(item);
        if (itemIndex == -1) {
            ArrayList<Item> newItem = new ArrayList<>();
            newItem.add(item);
            this.inventory.add(newItem);
        } else {
            this.inventory.get(itemIndex).add(item);
        }
    }
    //can cause index out of bounds
    public void checkInventory(boolean death) {

        if (this.inventory.isEmpty()) {
            if (death) {
                System.out.println("Wow! Not leaving anything for the next person...");
            } else {
                System.out.println("Your inventory is empty!");
            }
            return;
        }

        for (int i = 0; i < this.inventory.size(); i++) {
            //Displays amount of items in parentheses (e.g. (x2)) if the amount is greater than 1
            String amount = (this.inventory.get(i).size() > 1) ? " (x" + this.inventory.get(i).size() + ")" : "";

            System.out.println((i+1) + ". " + this.inventory.get(i).getFirst().getName() +
                    amount
                    + ": " + this.inventory.get(i).getFirst().getDescription());
            System.out.println();
        }

        if (death) {
            return;
        }

        System.out.println("Would you like to use an item?\nInput the appropriate number to use it, or exit to return to gameplay.");
        int response = Main.responseHandler(this, 1, this.inventory.size());

        if (response == -1) {
            System.out.println("You exit your inventory.");
            return;
        }

        inventory.get(response - 1).getFirst().useItem(this);
    }

    //this is very dry, but I wasn't sure how to use checkInventory due to the different types
    public void checkRelics(boolean death) {

        if (this.equippedRelics.isEmpty()) {
            if (death) {
                System.out.println("Too good for those darn relics, eh?");
            } else {
                System.out.println("Your relic pouch is empty!");
            }
            return;
        }

        for (int i = 0; i < this.equippedRelics.size(); i++) {
            System.out.println((i+1) + ". " + this.equippedRelics.get(i).name + ": "
                    + this.equippedRelics.get(i).description);
            System.out.println();
        }

        if (death) {
            return;
        }

        System.out.println("Would you like to unequip a relic?\nInput the appropriate number to do so, or exit to return to gameplay.");
        int response = Main.responseHandler(this, 1, this.equippedRelics.size());

        if (response == -1) {
            System.out.println("You shut your relic pouch.");
            return;
        }

        equippedRelics.get(response - 1).useItem(this);
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
            if (equippedRelic.name.equals(relicName)) {
                return this.equippedRelics.indexOf(equippedRelic);
            }
        }
        return -1;
    }

    public void checkStatus() {
        System.out.println("Current player status:");
        System.out.println("Health: " + this.currentHealth + "/" + this.maxHealth);
        System.out.println("Total damage output: " + this.damage);
    }
    public void takeDamage(int damage) {
        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
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

    public void useRelics(Room room) {
        for (Relic equippedRelic : this.equippedRelics) {
            equippedRelic.useRelic(this, room);
        }
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
        System.out.println("You died on room #" + this.roomsTraversed + ".");
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
}