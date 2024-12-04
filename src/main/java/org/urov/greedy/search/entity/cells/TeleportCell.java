package org.urov.greedy.search.entity.cells;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("TeleportCell")
public class TeleportCell extends Cell {
    private boolean isEntry;
    private int pairId;
    private TeleportCell linkedTeleport;

    public TeleportCell(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        super(x, y);
        isEntry = false;
        pairId = 0;
    }

    public TeleportCell(@JsonProperty("x") int x, @JsonProperty("y") int y, boolean isEntry, int pairId) {
        super(x, y);
        this.isEntry = isEntry;
        this.pairId = pairId;
    }

    public boolean isEntry() {
        return isEntry;
    }

    public int getPairId() {
        return pairId;
    }

    public void setLinkedTeleport(TeleportCell linkedTeleport) {
        if (linkedTeleport == null) {
            throw new IllegalArgumentException("Linked teleport cannot be null.");
        }
        if (linkedTeleport.getPairId() != this.pairId) {
            throw new IllegalArgumentException("Pair ID mismatch: " + this.pairId + " and " + linkedTeleport.getPairId());
        }
        if (linkedTeleport == this) {
            throw new IllegalArgumentException("A teleport cannot link to itself.");
        }

        this.linkedTeleport = linkedTeleport;
    }

    @Override
    public String toString() {
        return String.valueOf(pairId);
    }
}