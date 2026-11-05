package main.item.health;

import main.entity.Player;

public class AppleItem extends HealthItem {
    public AppleItem() {
        this(1);
    }

    public AppleItem(double dropChance) {
        super("Apple",
                "An apple you found in the cave system. It doesn't seem too fresh.",
                3,
                dropChance,
                12,
                -1,
                6,
                0,
                true);
    }

    @Override
    public boolean cleanseItem(Player player) {
        super.cleanseItem(player);
        player.discardItem(this);
        player.addItemToInventory(new PureAppleItem());
        return true;
    }

}
