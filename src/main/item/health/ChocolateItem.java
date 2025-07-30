package main.item.health;

public class ChocolateItem extends HealthItem{
    public ChocolateItem() {
        this.setName("Torpedo Chocolate Bar");
        this.setDescription("A legendary chocolate bar thought to be long-lost. You're salivating just looking at it.");
        this.setRestorationRange(5, 15);
        this.setAddedAbsorption(3);
        this.setValue(10);
        this.setShopWeight(6);
    }
}
