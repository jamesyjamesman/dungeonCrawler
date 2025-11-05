package main.item.relic;

public enum RelicID {
    CURSE_HEAL,
    CURSE_DETECTION,
    ENEMY_INFORMATION,
    FORESIGHT,
    HOLDING,
    REGENERATION,
    RELICS,
    SHIELDING,
    SLIME,
    CURE,
    DAMAGE;

    public RelicID stringToID(String string) {
        return RelicID.valueOf(string.toUpperCase());
    }
}
