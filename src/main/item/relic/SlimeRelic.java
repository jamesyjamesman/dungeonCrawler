package main.item.relic;

//logic handled in Enemy.attack()
public class SlimeRelic extends Relic {
    public SlimeRelic() {
        this(1);
    }
    public SlimeRelic(double dropChance) {
        super("Relic of Bounciness",
                "This relic covers you in viscous, bouncy slime. Some attacks just bounce right off!",
                dropChance,
                RelicID.SLIME);
    }
}
