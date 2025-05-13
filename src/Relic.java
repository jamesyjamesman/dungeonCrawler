public abstract class Relic extends Item{
    boolean equipped;
    public Relic(){
        this.equipped = false;
    }
    public abstract void useRelic(Player player, Room room);

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
    }

    public void unequipRelic(Player player){
        this.equipped = false;
        player.equippedRelics.remove(this);
        player.addItemToInventory(this);
    }
}
