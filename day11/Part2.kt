package day11

import java.io.File
import kotlin.math.abs

fun main() {
    val lines = File("day11/input.txt").readLines()
    val galaxies = mutableListOf<Pair<Int, Int>>()
    identifyGalaxies(lines, galaxies)
    val totalPathLength = calculateTotalPathLengthWithExpansion(lines, galaxies, 1000000)
    println("Total length of all shortest paths: $totalPathLength") // 458191688761
}

fun identifyGalaxies(lines: List<String>, galaxies: MutableList<Pair<Int, Int>>) {
    lines.forEachIndexed { rowIndex, line ->
        line.forEachIndexed { colIndex, char ->
            if (char == '#') galaxies.add(Pair(rowIndex, colIndex))
        }
    }
}

fun calculateTotalPathLengthWithExpansion(lines: List<String>, galaxies: List<Pair<Int, Int>>, expansionFactor: Int): Long {
    var totalLength = 0L

    val emptyRows = lines.indices.filter { row -> lines[row].all { it == '.' } }.toSet()
    val emptyCols = lines[0].indices.filter { col -> lines.all { it[col] == '.' } }.toSet()

    for (i in galaxies.indices) {
        for (j in i + 1 until galaxies.size) {
            totalLength += findShortestPathLengthWithExpansion(galaxies[i], galaxies[j], emptyRows, emptyCols, expansionFactor)
        }
    }
    return totalLength
}

fun findShortestPathLengthWithExpansion(start: Pair<Int, Int>, end: Pair<Int, Int>, emptyRows: Set<Int>, emptyCols: Set<Int>, expansionFactor: Int): Long {
    val (startX, startY) = start
    val (endX, endY) = end

    val dx = abs(startX - endX)
    val expandedDx = if (dx == 0) 0 else dx + emptyRows.count { it in (minOf(startX, endX) + 1) until maxOf(startX, endX) } * (expansionFactor - 1)

    val dy = abs(startY - endY)
    val expandedDy = if (dy == 0) 0 else dy + emptyCols.count { it in (minOf(startY, endY) + 1) until maxOf(startY, endY) } * (expansionFactor - 1)

    return expandedDx.toLong() + expandedDy.toLong()
}
