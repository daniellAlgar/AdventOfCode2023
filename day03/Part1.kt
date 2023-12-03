package day03

import java.io.File

fun main() {
    val schematic = File("day03/input.txt").readLines()
    println("Sum of part numbers: ${sumPartNumbers(schematic)}") // 521601
}

fun sumPartNumbers(schematic: List<String>): Int {
    var sum = 0
    val visited = Array(schematic.size) { BooleanArray(schematic[0].length) }

    for (i in schematic.indices) {
        for (j in schematic[i].indices) {
            if (!visited[i][j] && schematic[i][j].isDigit()) {
                val number = extractNumber(schematic, i, j, visited)
                if (isAdjacentToSymbol(schematic, i, j, number.length)) {
                    sum += number.toInt()
                }
            }
        }
    }

    return sum
}

fun extractNumber(schematic: List<String>, i: Int, j: Int, visited: Array<BooleanArray>): String {
    val number = StringBuilder()
    var x = j
    while (x < schematic[i].length && schematic[i][x].isDigit()) {
        number.append(schematic[i][x])
        visited[i][x] = true
        x++
    }
    return number.toString()
}

fun isAdjacentToSymbol(schematic: List<String>, i: Int, j: Int, length: Int): Boolean {
    for (x in i - 1..i + 1) {
        for (y in j - 1..j + length) {
            if (x in schematic.indices && y in schematic[x].indices && schematic[x][y] != '.' && !schematic[x][y].isDigit()) {
                return true
            }
        }
    }

    return false
}