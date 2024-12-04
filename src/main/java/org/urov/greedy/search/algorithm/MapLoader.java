package org.urov.greedy.search.algorithm;

import org.urov.greedy.search.entity.Maze;
import org.urov.greedy.search.entity.cells.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class MapLoader {
    public Maze loadMap(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        Cell[][] map = new Cell[lines.size()][lines.get(0).length()];
        Map<Integer, List<Cell>> teleportPairs = new HashMap<>();

        for (int y = 0; y < lines.size(); y++) {
            String[] row = lines.get(y).split(" ");
            for (int x = 0; x < row.length; x++) {
                switch (row[x]) {
                    case "*":
                        map[y][x] = new WalkableCell(x, y);
                        break;
                    case "%":
                        map[y][x] = new ObstacleCell(x, y);
                        break;
                    case "S":
                        map[y][x] = new StartCell(x, y);
                        break;
                    case "E":
                        map[y][x] = new EndCell(x, y);
                        break;
                }
            }
        }

        for (String line : lines) {
            if (line.startsWith("t")) {
                String[] parts = line.split(" ");
                String[] coords1 = parts[1].split(",");
                String[] coords2 = parts[2].split(",");
                int x1 = Integer.parseInt(coords1[0]);
                int y1 = Integer.parseInt(coords1[1]);
                int x2 = Integer.parseInt(coords2[0]);
                int y2 = Integer.parseInt(coords2[1]);

                TeleportCell teleport1 = new TeleportCell(x1, y1, true, 0); // true для входного
                TeleportCell teleport2 = new TeleportCell(x2, y2, false, 0); // false для выходного

                map[y1][x1] = teleport1;
                map[y2][x2] = teleport2;

                teleportPairs.put(0, Arrays.asList(teleport1, teleport2));
            }
        }

        int notNullForX = 0;
        while (map[0][notNullForX] != null) {
            notNullForX++;
        }

        int notNullForY = 0;
        while (map[notNullForY][0] != null) {
            notNullForY++;
        }

        Cell[][] goodMap = new Cell[notNullForY][notNullForX];
        for (int i = 0; i < notNullForY; i++) {
            for (int j = 0; j < notNullForX; j++) {
                goodMap[i][j] = map[i][j];
            }
        }

        for (Cell[] cells : goodMap) {
            System.out.println(Arrays.toString(cells));
        }

        for (Map.Entry<Integer, List<Cell>> entry : teleportPairs.entrySet()) {
            if (entry.getValue().size() != 2) {
                for (Cell cell : entry.getValue()) {
                    System.out.println(cell.getX() + " " + cell.getY());
                }
                throw new IllegalStateException("Invalid teleport pair for ID: " + entry.getKey());
            }
        }

        return new Maze(map);
    }

    public static Maze createMazeFromArray(String[][] inputMaze) {
        int rows = inputMaze.length;
        int cols = inputMaze[0].length;

        Cell[][] map = new Cell[rows][cols];
        Map<Integer, List<TeleportCell>> teleportPairs = new HashMap<>();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                String cell = inputMaze[y][x];

                switch (cell) {
                    case "S":
                        map[y][x] = new StartCell(x, y);
                        break;
                    case "E":
                        map[y][x] = new EndCell(x, y);
                        break;
                    case "*":
                        map[y][x] = new WalkableCell(x, y);
                        break;
                    case "%":
                        map[y][x] = new ObstacleCell(x, y);
                        break;
                    default:
                        if (cell.startsWith("t_")) {
                            // Разбираем строку телепорта
                            String[] parts = cell.split("_");
                            if (parts.length != 3) {
                                throw new IllegalArgumentException("Invalid teleport format: " + cell);
                            }

                            int pairId = Integer.parseInt(parts[1]);
                            boolean isEntry = parts[2].equals("1");

                            TeleportCell teleport = new TeleportCell(x, y, isEntry, pairId);
                            map[y][x] = teleport;

                            // Добавляем телепорт в соответствующую пару
                            teleportPairs.computeIfAbsent(pairId, k -> new ArrayList<>()).add(teleport);
                        } else {
                            throw new IllegalArgumentException("Unknown cell type: " + cell);
                        }
                }
            }
        }

        // Проверяем корректность пар телепортов
        for (Map.Entry<Integer, List<TeleportCell>> entry : teleportPairs.entrySet()) {
            List<TeleportCell> pair = entry.getValue();
            if (pair.size() != 2) {
                throw new IllegalArgumentException("Invalid teleport pair for id " + entry.getKey() + ": " + pair);
            }

            TeleportCell entryTeleport = pair.stream().filter(TeleportCell::isEntry).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No entry teleport for pair id " + entry.getKey()));
            TeleportCell exitTeleport = pair.stream().filter(teleport -> !teleport.isEntry()).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No exit teleport for pair id " + entry.getKey()));

            entryTeleport.setLinkedTeleport(exitTeleport);
        }

        return new Maze(map); // Возвращаем созданный лабиринт
    }


}
