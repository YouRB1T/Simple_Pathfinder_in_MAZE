package org.urov.greedy.search.algorithm;

import org.urov.greedy.search.entity.cells.Cell;

import java.util.*;

public class GreedySearch {
    public static List<Cell> findPath(Cell start, Cell goal, Map<Cell, List<Cell>> graph) {
        PriorityQueue<Cell> openSet = new PriorityQueue<>(Comparator.comparingDouble(c -> heuristic(c, goal)));
        Set<Cell> closedSet = new HashSet<>();
        Map<Cell, Cell> cameFrom = new HashMap<>();

        openSet.add(start);

        while (!openSet.isEmpty()) {
            Cell current = openSet.poll();

            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(current);

            for (Cell neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                if (!openSet.contains(neighbor)) {
                    cameFrom.put(neighbor, current);
                    openSet.add(neighbor);
                }
            }
        }
        return Collections.emptyList();
    }

    private static double heuristic(Cell a, Cell b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private static List<Cell> reconstructPath(Map<Cell, Cell> cameFrom, Cell current) {
        List<Cell> path = new ArrayList<>();
        while (current != null) {
            path.add(0, current);
            current = cameFrom.get(current);
        }
        return path;
    }
}

