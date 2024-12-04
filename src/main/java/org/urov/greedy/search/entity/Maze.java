package org.urov.greedy.search.entity;

import org.urov.greedy.search.entity.cells.*;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")

public class Maze {
    private Cell[][] maze;

    public Maze() {
    }

    public Maze(Cell[][] maze) {
        this.maze = maze;
    }

    public Cell[][] getMaze() {
        return maze;
    }

    public void setMaze(Cell[][] maze) {
        this.maze = maze;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();

        for (Cell[] cells : maze) {
            stringBuilder.append(Arrays.toString(cells)).append("\n");
        }

        return stringBuilder.toString();
    }

    public void setCell(Cell cell) {
        maze[cell.getY()][cell.getX()] = cell;
    }
}
