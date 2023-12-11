package day11

import java.io.File
import kotlin.math.abs

fun main() {
    val lines = File("day11/input.txt").readLines()
    val galaxies = mutableListOf<Pair<Int, Int>>()
    expandUniverse(lines, galaxies)
    val totalPathLength = calculateTotalPathLengthUsingManhattanDistance(galaxies)
    println("Total length of all shortest paths: $totalPathLength") // 9605127
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

fun calculateTotalPathLengthUsingManhattanDistance(galaxies: List<Pair<Int, Int>>): Int {
    var totalLength = 0
    for (i in galaxies.indices) {
        for (j in i + 1 until galaxies.size) {
            totalLength += calculateManhattanDistance(galaxies[i], galaxies[j])
        }
    }
    return totalLength
}

fun calculateManhattanDistance(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    return abs(start.first - end.first) + abs(start.second - end.second)
}