package org.urov.greedy.search.rest.services;

import org.springframework.stereotype.Service;
import org.urov.greedy.search.entity.cells.Cell;

@Service
public class CellService {

    private Cell[] cells;
    Integer[] integers;// Одномерный массив клеток

    // Инициализация массива клеток
    public void initializeCells(int size) {
        cells = new Cell[size];
    }

    // Создание клетки по индексу
    public void createCell(int index, Cell cell) {
        if (index >= 0 && index < cells.length) {
            cells[index] = cell;
        }
    }

    public void create(Cell[] cells) {
        this.cells = cells;
    }

    public void create(Integer[] integers) {
        this.integers = integers;
    }

    // Получение клетки по индексу
    public Cell getCell(int index) {
        if (index >= 0 && index < cells.length) {
            return cells[index];
        }
        return null;
    }

    // Получение всех клеток
    public Cell[] getAllCells() {
        return cells;
    }

    // Обновление клетки по индексу
    public void updateCell(int index, Cell updatedCell) {
        if (index >= 0 && index < cells.length) {
            cells[index] = updatedCell;
        }
    }

    // Удаление клетки по индексу
    public void deleteCell(int index) {
        if (index >= 0 && index < cells.length) {
            cells[index] = null;
        }
    }
}
