import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    String name;
    int maxHealth;
    int currentHealth;
    ArrayList<Item> inventory;
    public Player(String newName) {
        this.name = newName;
        this.maxHealth = 20;
        this.currentHealth = 20;
        this.inventory = new ArrayList<>();
    }

    public void addItemToInventory(Item item) {
        this.inventory.add(item);
    }

    public void checkInventory() {

        if (this.inventory.isEmpty()) {
            System.out.println("Your inventory is empty!");
            return;
        }

        Scanner lineScanner = new Scanner(System.in);
        for (int i = 0; i < this.inventory.size(); i++) {
            System.out.println((i+1) + ". " + this.inventory.get(i).name + ": " + this.inventory.get(i).description);
        }

        System.out.println("Would you like to use an item? Input the appropriate number to use it, or exit to return to gameplay.");
        String response = Main.inputHelper(this, lineScanner.nextLine());

        if (response.equals("exit")) {
            System.out.println("You exit your inventory.");
            return;
        }

        inventory.get(Integer.parseInt(response) - 1).useItem(this);

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
