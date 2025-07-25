package main.item.relic;

public class EnemyRelic extends Relic {
    public EnemyRelic() {
        setName("Relic of Enemy Information");
        setType(RelicID.ENEMY_INFORMATION);
        setDescription("This relic allows you to detect some extra information about your enemies.");
    }
}
