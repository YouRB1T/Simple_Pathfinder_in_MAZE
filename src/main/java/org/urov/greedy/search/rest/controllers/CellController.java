package org.urov.greedy.search.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.urov.greedy.search.entity.cells.Cell;
import org.urov.greedy.search.rest.services.CellService;

@RestController
@RequestMapping("/api/cells")
public class CellController {

    private final CellService cellService;

    @Autowired
    public CellController(CellService cellService) {
        this.cellService = cellService;
    }

    // Инициализация массива клеток
    @PostMapping("/initialize")
    public ResponseEntity<?> initializeCells(@RequestParam int size) {
        cellService.initializeCells(size);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCell(@RequestBody Cell[] cells) {
        cellService.create(cells);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    // Получение клетки по индексу
    @GetMapping("/get")
    public ResponseEntity<Cell> getCell(@RequestParam int index) {
        Cell cell = cellService.getCell(index);
        return cell != null
                ? new ResponseEntity<>(cell, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Получение всех клеток
    @GetMapping("/all")
    public ResponseEntity<Cell[]> getAllCells() {
        Cell[] cells = cellService.getAllCells();
        return new ResponseEntity<>(cells, HttpStatus.OK);
    }

    // Обновление клетки по индексу
    @PutMapping("/update")
    public ResponseEntity<?> updateCell(@RequestParam int index, @RequestBody Cell updatedCell) {
        cellService.updateCell(index, updatedCell);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Удаление клетки по индексу
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCell(@RequestParam int index) {
        cellService.deleteCell(index);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
