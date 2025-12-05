package main.item.relic;

import main.entity.Player;

public class EnemyRelic extends Relic {
    public EnemyRelic() {
        this(1);
    }
    public EnemyRelic(double dropChance) {
        super("Relic of Enemy Information",
                "This relic allows you to detect some extra information about your enemies.",
                dropChance,
                RelicID.ENEMY_INFORMATION);
    }

    @Override
    public String useRelic(Player player) {
        return "";
    }
}
