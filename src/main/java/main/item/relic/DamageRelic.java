package main.item.relic;

public class DamageRelic extends Relic {
    private final int damage;
    public DamageRelic(int damage) {
        this(1, damage);
    }
    public DamageRelic(double dropChance, int damage) {
        super("Enchanted Dragon Scale",
                "An enchanted scale of a dragon. Its bearer gains the strength of its former user.",
                dropChance,
                RelicID.DAMAGE);
        this.damage = damage;
    }
    public int getDamage() {
        return this.damage;
    }
}
