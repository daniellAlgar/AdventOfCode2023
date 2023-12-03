package day03

import java.io.File

fun main() {
    val schematic = File("day03/input.txt").readLines()
    val partNumbers = findAllPartNumbers(schematic)
    println("Sum of gear ratios: ${sumGearRatios(schematic, partNumbers)}") // 80694070
}

fun sumGearRatios(schematic: List<String>, partNumbers: Map<Pair<Int, Int>, Int>): Int {
    var totalGearRatio = 0

    for (i in schematic.indices) {
        for (j in schematic[i].indices) {
            if (schematic[i][j] == '*') {
                val adjacentParts = findAdjacentPartNumbers(schematic, i, j, partNumbers)
                if (adjacentParts.size == 2) {
                    totalGearRatio += adjacentParts.elementAt(0) * adjacentParts.elementAt(1)
                }
            }
        }
    }

    return totalGearRatio
}

fun findAllPartNumbers(schematic: List<String>): Map<Pair<Int, Int>, Int> {
    val partNumbers = mutableMapOf<Pair<Int, Int>, Int>()

    for (i in schematic.indices) {
        var j = 0
        while (j < schematic[i].length) {
            if (schematic[i][j].isDigit()) {
                val number = extractNumber(schematic, i, j)
                partNumbers[Pair(i, j)] = number.toInt()
                j += number.length
            } else {
                j++
            }
        }
    }

    return partNumbers
}

fun findAdjacentPartNumbers(schematic: List<String>, i: Int, j: Int, partNumbers: Map<Pair<Int, Int>, Int>): Set<Int> {
    val adjacentParts = mutableSetOf<Int>()

    for (di in -1..1) {
        for (dj in -1..1) {
            if (di == 0 && dj == 0) continue
            val x = i + di
            val y = j + dj
            if (x in schematic.indices && y in schematic[x].indices && schematic[x][y].isDigit()) {
                partNumbers.forEach { (key, value) ->
                    if (key.first == x && key.second <= y && y < key.second + value.toString().length) {
                        adjacentParts.add(value)
                    }
                }
            }
        }
    }

    return adjacentParts
}

fun extractNumber(schematic: List<String>, i: Int, j: Int): String {
    val number = StringBuilder()

    var x = j
    while (x < schematic[i].length && schematic[i][x].isDigit()) {
        number.append(schematic[i][x])
        x++
    }

    return number.toString()
}