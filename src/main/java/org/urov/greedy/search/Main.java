package org.urov.greedy.search;

import org.urov.greedy.search.algorithm.GraphBuilder;
import org.urov.greedy.search.algorithm.GreedySearch;
import org.urov.greedy.search.algorithm.MapLoader;
import org.urov.greedy.search.entity.Maze;
import org.urov.greedy.search.entity.cells.Cell;
import org.urov.greedy.search.entity.cells.EndCell;
import org.urov.greedy.search.entity.cells.StartCell;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[][] maze1 = new String[][] {
                {"S", "*", "*", "*", "%"},
                {"*", "%", "*", "*", "t_1_2"},
                {"*", "t_1_1", "*", "%", "E"},
                {"*", "%", "*", "*", "%"}
        };

        Maze maze = MapLoader.createMazeFromArray(maze1);

        Cell start = Arrays.stream(maze.getMaze())
                .flatMap(Arrays::stream)
                .filter(cell -> cell instanceof StartCell)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Start not found"));

        Cell end =  Arrays.stream(maze.getMaze())
                .flatMap(Arrays::stream)
                .filter(cell -> cell instanceof EndCell)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Start not found"));

        List<Cell> cells = GreedySearch.findPath(start, end, GraphBuilder.buildGraph(maze.getMaze()));

        for (Cell cell : cells) {
            System.out.println(cell.toString());
        }

    }
}
