package main.item.weapon;

public class Mace extends Weapon {
    public Mace(double dropChance) {
        this.weaponType = WeaponType.MACE;
        this.setDropChance(dropChance);
        this.setDamage(2);
        this.setName("Rusty Mace");
        this.setDescription("A dilapidated old mace. You're surprised it still works!");
        this.setValue(4);
    }
}