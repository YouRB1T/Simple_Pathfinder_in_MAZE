package org.urov.greedy.search.entity.cells;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = WalkableCell.class, name = "WalkableCell"),
        @JsonSubTypes.Type(value = ObstacleCell.class, name = "ObstacleCell"),
        @JsonSubTypes.Type(value = StartCell.class, name = "StartCell"),
        @JsonSubTypes.Type(value = EndCell.class, name = "EndCell"),
        @JsonSubTypes.Type(value = TeleportCell.class, name = "TeleportCell")
})
public abstract class Cell {
    @JsonProperty("x")
    private final int x;
    @JsonProperty("y")
    private final int y;

    public Cell(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWalkable() {
        return this instanceof WalkableCell || this instanceof TeleportCell || this instanceof StartCell || this instanceof EndCell;
    }

    public String toString() {
        return "";
    }
}
