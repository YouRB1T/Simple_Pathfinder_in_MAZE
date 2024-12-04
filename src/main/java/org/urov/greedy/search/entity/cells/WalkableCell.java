package org.urov.greedy.search.entity.cells;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("WalkableCell")
public class WalkableCell extends Cell {
    public WalkableCell(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "*";
    }
}
