public class Player {
    String name;
    int maxHealth;
    int currentHealth;
    public Player(String newName) {
        this.name = newName;
        this.maxHealth = 20;
        this.currentHealth = 20;
    }

    public void checkStatus() {
        System.out.println("Current player status:");
        //can this just be this.currentHealth and then I can avoid having to pass the object to itself?
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
