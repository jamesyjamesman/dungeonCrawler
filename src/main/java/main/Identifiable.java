package main;

import java.util.UUID;

public class Identifiable {
    private final UUID uuid;
    public Identifiable() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
