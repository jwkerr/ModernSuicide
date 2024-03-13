package com.fwloopins.modernsuicide.object;

import java.util.UUID;

public class PlayerData {
    private final UUID uuid;
    private int totalSuicides;

    public PlayerData(UUID uuid, int totalSuicides) {
        this.uuid = uuid;
        this.totalSuicides = totalSuicides;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public int getTotalSuicides() {
        return this.totalSuicides;
    }

    public void setTotalSuicides(int value) {
        this.totalSuicides = value;
    }

    public void incrementSuicides() {
        this.totalSuicides++;
    }
}
