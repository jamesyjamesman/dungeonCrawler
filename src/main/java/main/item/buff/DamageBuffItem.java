package main.item.buff;

import main.entity.Player;
import main.requests.ItemUseCase;

public class DamageBuffItem extends BuffItem {
    public DamageBuffItem() {
        this(1, 1, 3);
    }
    public DamageBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public DamageBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "attack damage");
    }

    @Override
    public ItemUseCase useItem(Player player) {
        player.increaseDamage(this.amountChanged);
        player.discardItem(this);
        return ItemUseCase.BUFF;
    }
}
