import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    String name;
    int maxHealth;
    int currentHealth;
    ArrayList<ArrayList<Item>> inventory;
    public Player(String newName) {
        this.name = newName;
        this.maxHealth = 20;
        this.currentHealth = 20;
        this.inventory = new ArrayList<>();
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
            }
            System.out.println("Your inventory is empty!");
            return;
        }

        Scanner lineScanner = new Scanner(System.in);
        for (int i = 0; i < this.inventory.size(); i++) {
            System.out.println((i+1) + ". " + this.inventory.get(i).getFirst().name
                    //perhaps: hide amount if amount = 1
                    + " (x" + this.inventory.get(i).size() + ")" + ": "
                    + this.inventory.get(i).getFirst().description);
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

    public int findItemInInventory(Item item) {
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).getFirst().equals(item)) {
                return i;
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
    public void heal(int health) {
        this.currentHealth += health;
        if (this.currentHealth > this.maxHealth) {
            this.currentHealth = this.maxHealth;
        }
    }

    public void changeMaxHealth(int health) {
            this.maxHealth += health;
            this.currentHealth += health;
        }
    }
