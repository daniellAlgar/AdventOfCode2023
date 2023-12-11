package day10

import java.io.File

enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

data class Position(var x: Int, var y: Int) {
    fun move(direction: Direction) {
        when (direction) {
            Direction.NORTH -> y--
            Direction.SOUTH -> y++
            Direction.EAST -> x++
            Direction.WEST -> x--
        }
    }
}

fun main() {
    val lines = File("day10/input.txt").readLines()
    val grid = lines.map { it.toCharArray() }.toTypedArray()

    val initialPipeType = 'L'
    val initialDirection = Direction.NORTH

    val startPosition = findStartPosition(grid)
    grid[startPosition.y][startPosition.x] = initialPipeType

    var currentPosition = startPosition
    var nextDirection = initialDirection
    var steps = 0
    while (true) {
        ++steps
        val nextPosition = currentPosition.copy().apply { move(nextDirection) }
        if (nextPosition == startPosition) break
        nextDirection = getNextDirection(grid, nextPosition, nextDirection)
        currentPosition = nextPosition
    }

    println("Steps to furthest point: ${steps / 2}") // 6886
}

fun getNextDirection(grid: Array<CharArray>, position: Position, currentDirection: Direction): Direction {
    return when (val tile = grid[position.y][position.x]) {
        '|' -> when (currentDirection) {
            Direction.NORTH, Direction.SOUTH -> currentDirection
            else -> throw IllegalStateException("Invalid direction for vertical pipe")
        }

        '-' -> when (currentDirection) {
            Direction.EAST, Direction.WEST -> currentDirection
            else -> throw IllegalStateException("Invalid direction for horizontal pipe")
        }

        'L' -> when (currentDirection) {
            Direction.SOUTH -> Direction.EAST
            Direction.WEST -> Direction.NORTH
            else -> throw IllegalStateException("Invalid direction for L bend")
        }

        'J' -> when (currentDirection) {
            Direction.SOUTH -> Direction.WEST
            Direction.EAST -> Direction.NORTH
            else -> throw IllegalStateException("Invalid direction for J bend")
        }

        '7' -> when (currentDirection) {
            Direction.NORTH -> Direction.WEST
            Direction.EAST -> Direction.SOUTH
            else -> throw IllegalStateException("Invalid direction for 7 bend")
        }

        'F' -> when (currentDirection) {
            Direction.WEST -> Direction.SOUTH
            Direction.NORTH -> Direction.EAST
            else -> throw IllegalStateException("Invalid direction for F bend")
        }

        else -> throw IllegalStateException("Unknown pipe type: $tile")
    }
}

fun findStartPosition(grid: Array<CharArray>): Position {
    for (y in grid.indices) {
        for (x in grid[0].indices) {
            if (grid[y][x] == 'S') {
                return Position(x, y)
            }
        }
    }
    throw IllegalStateException("Start position 'S' not found in the grid")
}
