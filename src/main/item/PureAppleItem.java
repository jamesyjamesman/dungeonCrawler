package main.item;

public class PureAppleItem extends HealthItem {
    public PureAppleItem() {
        this.name = "Pure Apple";
        this.description = "The purified water cleansed any rot this apple once had. It looks perfect!";
        setRestorationRange(5, 10);
    }
}
