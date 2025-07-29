package main.item;

public class DummyItem extends Item {
    public DummyItem() {
        this.setName("Dummy");
        this.setValue(100000);
        this.setDescription("If this item is encountered, a bug has occurred.");
    }
}
