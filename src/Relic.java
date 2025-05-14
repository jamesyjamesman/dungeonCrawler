import java.util.Random;

public abstract class Relic extends Item{
    boolean equipped;
    boolean cursed;
    public Relic(){
        this.equipped = false;
        this.cursed = new Random().nextInt(5) == 0;
    }
    public void useRelic(Player player, Room room) {
        if (this.cursed) {
            int amountDamage = new Random().nextInt(5);
            System.out.println("Your cursed " + this.name + " caused 1 damage!");
            player.takeDamage(amountDamage);
        }
    }

    @Override
    public void useItem(Player player) {
        if(this.equipped) {unequipRelic(player);}
        else {equipRelic(player);}
    }

    public void equipRelic(Player player){
        this.equipped = true;
        player.equippedRelics.add(this);
        int relicIndex = player.findItemInInventory(this);
        player.inventory.remove(relicIndex);
        if (this.cursed) {
            System.out.println("Oh no! the " + this.name + " was cursed!");
        }
    }

    public void unequipRelic(Player player){
        if (this.cursed) {
            System.out.println("The relic is welded to you painfully. You can't remove it!");
            return;
        }
        this.equipped = false;
        player.equippedRelics.remove(this);
        player.addItemToInventory(this);
    }
}
