package day09

import java.io.File

fun main() {
    val lines = File("day09/input.txt").readLines()
    println("Sum of extrapolated values: ${calculateSum(lines)}") // 1938731307
}

fun calculateSum(lines: List<String>): Int {
    return lines.sumOf { findNextValue(it) }
}

fun findNextValue(line: String): Int {
    val numbers = line.split(" ").map { it.toInt() }
    val sequences = mutableListOf(numbers)

    while (sequences.last().distinct().size != 1 || sequences.last()[0] != 0) {
        sequences.add(generateDifferences(sequences.last()))
    }

    for (i in sequences.size - 2 downTo 0) {
        val nextValue = sequences[i].last() + sequences[i + 1].last()
        sequences[i] = sequences[i] + nextValue
    }

    return sequences.first().last()
}

fun generateDifferences(numbers: List<Int>): List<Int> {
    return numbers.zipWithNext { a, b -> b - a }
}
