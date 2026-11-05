package main.item.buff;

import main.entity.Player;
import main.requests.ItemUseCase;

public class UnBuffItem extends BuffItem {
    public UnBuffItem() {
        this(1, 2, 7);
    }
    public UnBuffItem(int lowBound, int highBound) {
        this(1, lowBound, highBound);
    }
    public UnBuffItem(double dropChance, int lowBound, int highBound) {
        super(dropChance, lowBound, highBound, "poison");
    }

    @Override
    public ItemUseCase useItem(Player player) {
        super.useItem(player);
        player.getCurrentStatuses().addPoison(this.amountChanged);
        return ItemUseCase.BUFF;
    }
}
