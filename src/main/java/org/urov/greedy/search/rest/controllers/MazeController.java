package org.urov.greedy.search.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urov.greedy.search.algorithm.MapLoader;
import org.urov.greedy.search.entity.Maze;
import org.urov.greedy.search.entity.cells.Cell;
import org.urov.greedy.search.rest.services.MazeService;
import org.urov.greedy.search.rest.services.MazeServiceImpl;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/maze")
public class MazeController {
    private final MazeService mazeService;

    @Autowired
    public MazeController(MazeService mazeService) {
        this.mazeService = mazeService;
    }

    /**
     * Create new maze
     * @param maze
     * @return response answer
     */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody String[][] maze) {
        mazeService.create(MapLoader.createMazeFromArray(maze));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/path")
    public ResponseEntity<List<Cell>> findPath() {
        final List<Cell> path = mazeService.read();

        return path != null && !path.isEmpty()
                ? new ResponseEntity<>(path, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/maze")
    public ResponseEntity<String> getCell() {
        final String maze = mazeService.getMaze();

        return !Objects.equals(maze, "") && mazeService.getMaze() != null
                ? new ResponseEntity<>(maze, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateCells(@RequestBody Cell[] cells) {
        mazeService.update(cells);
        return ResponseEntity.ok("Maze updated successfully.");
    }


}
