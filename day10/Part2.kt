package day10

import java.io.File
import java.util.*

fun main() {
    val lines = File("day10/input.txt").readLines()
    val grid = lines.map { it.toCharArray() }.toTypedArray()

    val initialPipeType = 'L'
    val initialDirection = Direction.NORTH

    val startPosition = findStartPosition(grid)
    grid[startPosition.y][startPosition.x] = initialPipeType
    val positions = mutableListOf(startPosition)

    var currentPosition = startPosition
    var nextDirection = initialDirection
    var steps = 0
    while (true) {
        ++steps
        val nextPosition = currentPosition.copy().apply { move(nextDirection) }
        if (nextPosition == startPosition) break
        positions.add(nextPosition)
        nextDirection = getNextDirection(grid, nextPosition, nextDirection)
        currentPosition = nextPosition
    }

    val isPartOfLoop = Array(grid.size) { BooleanArray(grid[0].size) { false } }
    for (position in positions) {
        isPartOfLoop[position.y][position.x] = true
    }

    val cleanGrid = grid.copyOf()
    for (y in cleanGrid.indices) {
        for (x in grid[0].indices) {
            if (!isPartOfLoop[y][x]) {
                cleanGrid[y][x] = '.'
            }
        }
    }

    val expandedGrid = expandInput(cleanGrid)
    val isPartOfExtendedLoop = Array(expandedGrid.size) { BooleanArray(expandedGrid[0].size) { false } }
    for (y in expandedGrid.indices) {
        for (x in expandedGrid[0].indices) {
            if (expandedGrid[y][x] != '#' && expandedGrid[y][x] != '.') {
                isPartOfExtendedLoop[y][x] = true
            }
        }
    }

    val visited = Array(expandedGrid.size) { BooleanArray(expandedGrid[0].size) { false } }
    for (x in expandedGrid[0].indices) {
        floodFill(expandedGrid, visited, isPartOfExtendedLoop, Position(x, 0))
        floodFill(expandedGrid, visited, isPartOfExtendedLoop, Position(x, expandedGrid.size - 1))
    }
    for (y in expandedGrid.indices) {
        floodFill(expandedGrid, visited, isPartOfExtendedLoop, Position(0, y))
        floodFill(expandedGrid, visited, isPartOfExtendedLoop, Position(expandedGrid[0].size - 1, y))
    }

    var enclosedTiles = 0
    for (y in expandedGrid.indices) {
        for (x in expandedGrid[0].indices) {
            if (!visited[y][x] && !isPartOfExtendedLoop[y][x] && expandedGrid[y][x] != '#')
                enclosedTiles++
        }
    }

    println("Number of enclosed tiles: $enclosedTiles") // 371
}

fun floodFill(
    grid: Array<CharArray>,
    visited: Array<BooleanArray>,
    isPartOfLoop: Array<BooleanArray>,
    start: Position
) {
    val stack = Stack<Position>()
    stack.push(start)

    while (stack.isNotEmpty()) {
        val pos = stack.pop()

        if (pos.x !in grid[0].indices || pos.y !in grid.indices) continue
        if (visited[pos.y][pos.x] || isPartOfLoop[pos.y][pos.x]) continue

        visited[pos.y][pos.x] = true

        stack.push(Position(pos.x + 1, pos.y))
        stack.push(Position(pos.x - 1, pos.y))
        stack.push(Position(pos.x, pos.y + 1))
        stack.push(Position(pos.x, pos.y - 1))
    }
}

fun expandInput(lines: Array<CharArray>): Array<CharArray> {
    val height = lines.size
    val width = lines[0].size
    val expanded = Array(height * 2) { CharArray(width * 2) { '#' } }

    for (y in 0 until height) {
        for (x in 0 until width) {
            val ch = lines[y][x]
            expanded[y * 2][x * 2] = ch

            // Horizontal expansion
            expanded[y * 2][x * 2 + 1] = if (ch == 'L' || ch == 'F' || ch == '-') {
                '-'
            } else {
                '#'
            }

            // Vertical expansion
            expanded[y * 2 + 1][x * 2] = if (ch == '|' || ch == 'F' || ch == '7') {
                '|'
            } else {
                '#'
            }
        }
    }

    return expanded
}
