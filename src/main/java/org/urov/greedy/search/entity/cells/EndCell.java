package org.urov.greedy.search.entity.cells;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("EndCell")
public class EndCell extends Cell {
    public EndCell(@JsonProperty("x") int x, @JsonProperty("y") int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "E";
    }
}