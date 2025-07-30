package main.item.health;

public class PureAppleItem extends HealthItem {
    public PureAppleItem() {
        this.setName("Pure Apple");
        this.setDescription("The purified water cleansed any rot this apple once had. It looks perfect!");
        this.setValue(10);
        setRestorationRange(5, 10);
        this.setShopWeight(1);
    }
}
