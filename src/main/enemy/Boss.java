package main.enemy;

import main.Player;
import main.item.Relic;

public abstract class Boss extends Enemy {
    public Boss() {
    }

    public abstract void bossAbilities();
    public abstract Relic initializeBossRelic();
    public abstract void death(Player player);
}
