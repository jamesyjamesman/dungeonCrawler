package main.item;

import main.entity.Player;
import main.requests.ItemUseCase;

public interface Equippable {
    // Returns if it was successful or not
    ItemUseCase equip(Player player);
    ItemUseCase unequip(Player player);
    boolean isEquipped(Player player);
}
