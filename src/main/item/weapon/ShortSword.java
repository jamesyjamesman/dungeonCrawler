package main.item.weapon;

public class ShortSword extends Weapon {
    public ShortSword() {
        this.setDamage(1);
        this.setName("Shortsword of Boringness");
        this.setDescription("A completely unremarkable short sword. " +
                "...Actually, this might just be a normal sword broken in half.");
        this.setValue(2);
    }
}
