package main.item.relic;

import main.entity.Player;

import java.util.Random;

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

    public static boolean slimeBounce(Player player) {
        return slimeBounce(player, 4);
    }

    public static boolean slimeBounce(Player player, int randCeiling) {
        return player.hasRelicEquipped(RelicID.SLIME) && new Random().nextInt(randCeiling) == 0;
    }

    @Override
    public String useRelic(Player player) {
        return "";
    }
}
