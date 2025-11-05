package main;

import java.util.UUID;

public class Identifiable {
    private final UUID uuid;
    public Identifiable() {
        this.uuid = UUID.randomUUID();
        System.out.println("UUID: " + this.uuid);
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
