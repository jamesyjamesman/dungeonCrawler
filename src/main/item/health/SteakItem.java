package main.item.health;

public class SteakItem extends HealthItem {
    public SteakItem() {
        this.setName("Steak");
        this.setDescription("A perfectly cooked steak.");
        this.setValue(7);
        this.setRestorationRange(4,9);
    }
}
