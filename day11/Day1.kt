package day11

import java.io.File
import java.util.*

fun main() {
    val lines = File("day11/input.txt").readLines()
    val galaxies = mutableListOf<Pair<Int, Int>>()
    val expandedLines = expandUniverse(lines, galaxies)
    println("Total length of all shortest paths: ${calculateTotalPathLength(expandedLines, galaxies)}") // 9605127
}

fun expandUniverse(lines: List<String>, galaxies: MutableList<Pair<Int, Int>>): List<String> {
    val emptyRows = lines.indices.filter { row -> lines[row].all { it == '.' } }
    val emptyCols = lines[0].indices.filter { col -> lines.all { it[col] == '.' } }

    val expandedLines = lines.toMutableList()
    for (row in emptyRows.reversed()) {
        expandedLines.add(row, expandedLines[row])
    }

    for (col in emptyCols.reversed()) {
        expandedLines.indices.forEach { row ->
            expandedLines[row] =
                expandedLines[row].substring(0, col) + expandedLines[row][col] + expandedLines[row].substring(col)
        }
    }

    // Label each galaxy
    expandedLines.forEachIndexed { rowIndex, line ->
        line.forEachIndexed { colIndex, char ->
            if (char == '#') galaxies.add(Pair(rowIndex, colIndex))
        }
    }
    return expandedLines
}

fun calculateTotalPathLength(lines: List<String>, galaxies: List<Pair<Int, Int>>): Int {
    var totalLength = 0
    for (i in galaxies.indices) {
        for (j in i + 1 until galaxies.size) {
            totalLength += findShortestPathLength(lines, galaxies[i], galaxies[j])
        }
    }
    return totalLength
}

fun findShortestPathLength(lines: List<String>, start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val queue = LinkedList<Pair<Pair<Int, Int>, Int>>()
    queue.offer(Pair(start, 0))

    while (queue.isNotEmpty()) {
        val (current, length) = queue.poll()
        if (current == end) return length

        if (!visited.add(current)) continue

        val (x, y) = current
        val directions = listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
        for (dir in directions) {
            val (nx, ny) = dir
            if (nx in lines.indices && ny in lines[0].indices && !visited.contains(dir)) {
                queue.offer(Pair(dir, length + 1))
            }
        }
    }
    return -1 // Shouldn't happen
}
