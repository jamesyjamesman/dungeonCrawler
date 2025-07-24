package main.item.weapon;

public class Wand extends Weapon {
    public Wand(double dropChance) {
        this.setDropChance(dropChance);
        this.setDamage(1);
        this.setName("Broken Wand");
        this.setDescription("A wand not capable of doing much more than shooting out short-lived sparks.");
        this.setValue(1);
    }
}
