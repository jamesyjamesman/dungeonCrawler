import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    String name;
    int maxHealth;
    int currentHealth;
    ArrayList<ArrayList<Item>> inventory;
    ArrayList<Relic> equippedRelics;
    public Player(String newName) {
        this.name = newName;
        this.maxHealth = 20;
        this.currentHealth = 20;
        this.inventory = new ArrayList<>();
        this.equippedRelics = new ArrayList<>();
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

    public void checkInventory(boolean death) {

        if (this.inventory.isEmpty()) {
            if (death) {
                System.out.println("Wow! Not leaving anything for the next person...");
            } else {
                System.out.println("Your inventory is empty!");
            }
            return;
        }

        Scanner lineScanner = new Scanner(System.in);
        for (int i = 0; i < this.inventory.size(); i++) {
            //Displays amount of items in parentheses (e.g. (x2)) if the amount is greater than 1
            String amount = (this.inventory.get(i).size() > 1) ? " (x" + this.inventory.get(i).size() + ")" : "";

            System.out.println((i+1) + ". " + this.inventory.get(i).getFirst().name +
                    amount
                    + ": " + this.inventory.get(i).getFirst().description);
            System.out.println();
        }

        if (death) {
            return;
        }

        System.out.println("Would you like to use an item?\nInput the appropriate number to use it, or exit to return to gameplay.");
        String response = Main.inputHelper(this, lineScanner.nextLine());

        if (response.equals("exit")) {
            System.out.println("You exit your inventory.");
            return;
        }

        inventory.get(Integer.parseInt(response) - 1).getFirst().useItem(this);
    }

    //this is very dry but i wasn't sure how to use checkInventory due to the different types
    public void checkRelics(boolean death) {

        if (this.equippedRelics.isEmpty()) {
            if (death) {
                System.out.println("Too good for those darn relics, eh?");
            } else {
                System.out.println("Your relic pouch is empty!");
            }
            return;
        }

        Scanner lineScanner = new Scanner(System.in);
        for (int i = 0; i < this.equippedRelics.size(); i++) {
            System.out.println((i+1) + ". " + this.equippedRelics.get(i).name + ": "
                    + this.equippedRelics.get(i).description);
            System.out.println();
        }

        if (death) {
            return;
        }

        System.out.println("Would you like to unequip a relic?\nInput the appropriate number to do so, or exit to return to gameplay.");
        String response = Main.inputHelper(this, lineScanner.nextLine());

        if (response.equals("exit")) {
            System.out.println("You shut your relic pouch.");
            return;
        }

        equippedRelics.get(Integer.parseInt(response) - 1).useItem(this);
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
    }
    public void takeDamage(int damage) {
        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
            Main.doDeathSequence(this);
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
}