var maze;

const CELL_SIZE = 50;

const cellColors = {
    "@": "#0fff0f",
    "%": "#000000",
    "*": "#ffffff",
    "0": "#00ff00",
    "1": "#0000ff",
    "S": "#ffff00",
    "E": "#ff0000"
};

function parseLabyrinth(data) {
    return data
        .trim()
        .split("\n")
        .map(row => row
            .replace(/[\[\]]/g, "")
            .split(",")
            .map(cell => cell.trim())
        );
}

function drawLabyrinth(canvas, labyrinth) {
    const ctx = canvas.getContext("2d");
    const rows = labyrinth.length;
    const cols = labyrinth[0].length;

    canvas.width = cols * CELL_SIZE;
    canvas.height = rows * CELL_SIZE;

    for (let y = 0; y < rows; y++) {
        for (let x = 0; x < cols; x++) {
            const cell = labyrinth[y][x];
            ctx.fillStyle = cellColors[cell] || "#cccccc";
            ctx.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);

            ctx.strokeStyle = "#000000";
            ctx.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }
}

function markPath(maze, pathData) {
    // Создаём копию лабиринта, чтобы не изменять оригинал
    const updatedMaze = maze.map(row => row.slice());

    // Помечаем клетки, которые входят в путь
    pathData.forEach(cell => {
        const { x, y } = cell;
        updatedMaze[y][x] = '@';  // Помечаем клетку символом "@"
    });

    return updatedMaze;
}

document.getElementById('fetch-maze').addEventListener('click', async () => {
    try {
        const response = await fetch('http://localhost:1234/api/maze/maze');
        if (!response.ok) throw new Error(`Failed to fetch maze: ${response.statusText}`);
        maze = parseLabyrinth(await response.text());
        console.log(maze);
        drawLabyrinth(document.getElementById('labyrinth'), maze);
    } catch (error) {
        console.error('Error fetching maze:', error);
    }
});

document.getElementById('create-maze').addEventListener('click', async () => {
    try {
        maze = [
            ['S', '*', '*', '*', '%'],
            ['*', '%', '*', '*', 't_0_1'],
            ['*', 't_0_2', '*', '%', 'E']
        ];
        const response = await fetch('http://localhost:1234/api/maze/create', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(maze)
        });
        if (!response.ok) throw new Error(`Failed to create maze: ${response.statusText}`);
        alert('Maze created successfully!');
    } catch (error) {
        console.error('Error creating maze:', error);
    }
});

document.getElementById('find-path').addEventListener('click', async () => {
    try {
// Шаг 1: Выполняем GET-запрос для получения данных о пути
     const response = await fetch('http://localhost:1234/api/maze/path');
     if (!response.ok) throw new Error('Failed to fetch path');
     const pathData = await response.json();

     const updatedMaze = markPath(maze, pathData); // Помечаем клетки на пути

        // Шаг 3: Отрисовываем обновлённый лабиринт
     drawLabyrinth(document.getElementById('labyrinth'), updatedMaze);
    } catch (error) {
        console.error('Error finding path in maze:', error)
    }
});


