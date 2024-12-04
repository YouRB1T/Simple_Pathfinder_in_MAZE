package org.urov.greedy.search.algorithm;

import org.urov.greedy.search.entity.cells.Cell;
import org.urov.greedy.search.entity.cells.TeleportCell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBuilder {
    public static Map<Cell, List<Cell>> buildGraph(Cell[][] map) {
        Map<Cell, List<Cell>> graph = new HashMap<>();

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                System.out.println(x + " " + y);
                Cell current = map[y][x];
                if (current.isWalkable()) {
                    List<Cell> neighbors = getNeighbors(map, x, y);
                    graph.put(current, neighbors);
                }
            }
        }
        return graph;
    }

    private static List<Cell> getNeighbors(Cell[][] map, int x, int y) {
        List<Cell> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx >= 0 && ny >= 0 && ny < map.length && nx < map[ny].length) {
                Cell neighbor = map[ny][nx];
                if (neighbor.isWalkable()) {
                    neighbors.add(neighbor);
                }
            }
        }

        if (map[y][x] instanceof TeleportCell teleport) {
            neighbors.addAll(findTeleportPair(map, teleport));
        }

        return neighbors;
    }

    private static List<Cell> findTeleportPair(Cell[][] map, TeleportCell teleport) {
        List<Cell> pairs = new ArrayList<>();
        for (Cell[] row : map) {
            for (Cell cell : row) {
                if (cell instanceof TeleportCell otherTeleport &&
                        otherTeleport.getPairId() == teleport.getPairId() &&
                        otherTeleport.isEntry() != teleport.isEntry()) {
                    pairs.add(otherTeleport);
                }
            }
        }
        return pairs;
    }
}