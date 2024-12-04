package org.urov.greedy.search.algorithm;

import org.urov.greedy.search.entity.cells.Cell;
import org.urov.greedy.search.entity.cells.TeleportCell;
import org.urov.greedy.search.entity.cells.ObstacleCell;

import java.util.*;

public class GreedySearch {
    public static List<Cell> findPath(Cell start, Cell goal, Map<Cell, List<Cell>> graph) {
        PriorityQueue<Cell> openSet = new PriorityQueue<>(Comparator.comparingDouble(c -> heuristic(c, goal)));
        Set<Cell> closedSet = new HashSet<>();
        Map<Cell, Cell> cameFrom = new HashMap<>();

        openSet.add(start);

        while (!openSet.isEmpty()) {
            Cell current = openSet.poll();

            // Если достигли цели, восстанавливаем путь
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            closedSet.add(current);

            for (Cell neighbor : graph.getOrDefault(current, Collections.emptyList())) {
                // Пропускаем клетки, которые уже посещены
                if (closedSet.contains(neighbor)) {
                    continue;
                }

                // Пропускаем ObstacleCell
                if (neighbor instanceof ObstacleCell) {
                    continue;
                }

                // Обработка телепортов
                if (neighbor instanceof TeleportCell) {
                    TeleportCell teleport = (TeleportCell) neighbor;
                    if (!teleport.isEntry()) {
                        continue; // Пропускаем выходной телепорт
                    }

                    // Телепортация к выходному телепорту
                    TeleportCell exitTeleport = findExitTeleport(graph.keySet(), teleport);
                    if (exitTeleport != null && !closedSet.contains(exitTeleport)) {
                        neighbor = exitTeleport; // Меняем сосед на выходной телепорт
                    }
                }

                // Добавляем в очередь для поиска, если сосед еще не в открытом наборе
                if (!openSet.contains(neighbor)) {
                    cameFrom.put(neighbor, current);
                    openSet.add(neighbor);
                }
            }
        }
        return Collections.emptyList(); // Путь не найден
    }

    // Эвристика: манхэттенское расстояние
    private static double heuristic(Cell a, Cell b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    // Поиск связанного выходного телепорта
    private static TeleportCell findExitTeleport(Set<Cell> allCells, TeleportCell entryTeleport) {
        for (Cell cell : allCells) {
            if (cell instanceof TeleportCell teleport && !teleport.isEntry() && teleport.getPairId() == entryTeleport.getPairId()) {
                return teleport;
            }
        }
        return null; // Выходной телепорт не найден
    }

    // Восстановление пути из карты cameFrom
    private static List<Cell> reconstructPath(Map<Cell, Cell> cameFrom, Cell current) {
        List<Cell> path = new ArrayList<>();
        while (current != null) {
            path.add(0, current);
            current = cameFrom.get(current);
        }
        return path;
    }
}
