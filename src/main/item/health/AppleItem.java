package main.item.health;

public class AppleItem extends HealthItem {
    public AppleItem() {
        this.setName("Apple");
        this.setDescription("An apple you found in the cave system. It doesn't seem too fresh.");
        this.setRestorationRange(-1, 6);
    }

}
