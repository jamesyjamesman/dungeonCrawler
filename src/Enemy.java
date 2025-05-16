public class Enemy {
    int currentHealth;
    int damage;
    int armor;
    int evasiveness;
    String species;
    String damageType;
    public Enemy() {
        this.currentHealth = 0;
        this.damage = 0;
        this.armor = 0;
        this.evasiveness = 0;
        this.species = "";
        this.damageType = "";
    }

    public boolean takeDamage(int damage) {
        this.currentHealth -= damage;
        if (this.currentHealth < 0) {
            this.currentHealth = 0;
            return true;
        }
        return false;
    }

    public void attack(Player player) {
        player.takeDamage(this.damage);
        System.out.println("The " + this.species + " attacked you, dealing " + this.damage + " damage!");
        //will get more complex with weapons, etc.
    }
}
