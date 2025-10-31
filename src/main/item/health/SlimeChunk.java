package main.item.health;

public class SlimeChunk extends HealthItem {
    public SlimeChunk() {
        this(1);
    }
    public SlimeChunk(double dropChance) {
        super("Chunk of Slime",
            "A chunk of blue slime. It smells sweet.",
            10,
            dropChance,
            0,
            3,
            10);
    }
}
