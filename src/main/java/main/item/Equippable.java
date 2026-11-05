package main.item;

import main.entity.Player;
import main.requests.ItemUseCase;

public interface Equippable {
    // Returns if it was successful or not
    public ItemUseCase equip(Player player);
    public ItemUseCase unequip(Player player);
    public boolean isEquipped(Player player);
}
