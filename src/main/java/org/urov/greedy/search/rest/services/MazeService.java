package org.urov.greedy.search.rest.services;

import org.urov.greedy.search.entity.Maze;
import org.urov.greedy.search.entity.cells.Cell;

import java.util.List;

public interface MazeService {
    void create(Maze maze);

    void update(Cell[] cells);

    List<Cell> read();

    boolean delete();

    String getMaze();
}
