package org.urov.greedy.search.entity.cells;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("StartCell")
public class StartCell extends Cell {
    public StartCell(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "S";
    }
}
