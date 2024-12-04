package org.urov.greedy.search.rest.services;

import org.springframework.stereotype.Service;
import org.urov.greedy.search.algorithm.GraphBuilder;
import org.urov.greedy.search.algorithm.GreedySearch;
import org.urov.greedy.search.entity.Maze;
import org.urov.greedy.search.entity.cells.*;

import java.util.*;

@Service
public class MazeServiceImpl implements MazeService{

    private Maze maze;
    private Cell startCell;
    private Cell endCell;


    @Override
    public void create(Maze maze) {
        this.maze = new Maze(maze.getMaze());
        for (Cell[] cells : maze.getMaze()) {
            System.out.println(Arrays.toString(cells));
        }
    }

    @Override
    public void update(Cell[] cells) {
        for (Cell cell : cells) {

            StringBuilder sb = new StringBuilder();
            sb.append("y = ").append(cell.getY()).append(", x = ").append(cell.getX())
                            .append("type = ").append(cell.getClass());
            System.out.println(sb.toString());

            maze.setCell(cell);
        }
    }

    @Override
    public List<Cell> read() {
        Map<Cell, List<Cell>> mazeGraph = GraphBuilder.buildGraph(maze.getMaze());

        startCell = Arrays.stream(maze.getMaze())
                .flatMap(Arrays::stream)
                .filter(cell -> cell instanceof StartCell)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Start not found"));

        endCell = Arrays.stream(maze.getMaze())
                .flatMap(Arrays::stream)
                .filter(cell -> cell instanceof EndCell)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("End not found"));

        List<Cell> path = GreedySearch.findPath(startCell, endCell, mazeGraph);

        return path;
    }

    @Override
    public boolean delete() {
        maze = new Maze();
        return true;
    }

    @Override
    public String getMaze() {
        maze =
        return maze.toString();
    }

}
