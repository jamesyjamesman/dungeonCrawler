public class DamageBuffItem extends BuffItem {
    public DamageBuffItem() {
        this.statType = "damage";
    }

    @Override
    public void useItem(Player player) {
        super.useItem(player);
        player.damage += this.amountChanged;
    }
}
