import java.util.Random;

public class HealthItem extends Item{
    int healthRestored;
    int[] restorationRange;
    int maxHealthChange;
    HealthItem() {
        this.healthRestored = 0;
        this.restorationRange = new int[2];
        this.maxHealthChange = 0;
    }

    @Override
    public void useItem(Player player) {
        this.healthRestored = new Random().nextInt(this.restorationRange[0], this.restorationRange[1]);

        if (this.healthRestored < 0) {
            System.out.println("Yuck! The " + this.name + " was terrible... You lost " + this.healthRestored * -1 + " health.");
            player.takeDamage(this.healthRestored * -1);
        } else {
            if (this.maxHealthChange != 0) {
                System.out.println("Wow! A surge of power courses through you... your maximum health has increased by " + this.maxHealthChange + "!");
                player.changeMaxHealth(this.maxHealthChange);
            }
            player.heal(this.healthRestored);
            System.out.println("Yum! That " + this.name + " was great! You replenished " + this.healthRestored + " health.");
        }

        int itemIndex = player.findItemInInventory(this);
        if (itemIndex == -1) {
            System.out.println("Error! This code should not be reachable (HealthItem.java)");
        }
        if (player.inventory.get(itemIndex).size() == 1) {
            player.inventory.remove(itemIndex);
        } else {
            player.inventory.get(itemIndex).remove(this);
        }
    }
}
