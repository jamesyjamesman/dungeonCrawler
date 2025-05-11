public class Player {
    String name;
    int maxHealth;
    int currentHealth;
    public Player(String newName) {
        this.name = newName;
        this.maxHealth = 20;
        this.currentHealth = 20;
    }

    public void checkStatus(Player player) {
        System.out.println("Current player status:");
        //can this just be this.currentHealth and then I can avoid having to pass the object to itself?
        System.out.println("Health: " + player.currentHealth + "/" + player.maxHealth);
    }
}
