package day09

import java.io.File

fun main() {
    val lines = File("day09/input.txt").readLines()
    println("Sum of previous extrapolated values: ${calculateSumPreviousValues(lines)}") // 948
}

fun calculateSumPreviousValues(lines: List<String>): Int =
    lines.sumOf { findPreviousValue(it) }

fun findPreviousValue(line: String): Int {
    val numbers = line.split(" ").map { it.toInt() }
    val sequences = mutableListOf(numbers)

    while (sequences.last().distinct().size != 1 || sequences.last()[0] != 0) {
        sequences.add(generateDifferences(sequences.last()))
    }

    for (i in sequences.size - 2 downTo 0) {
        val nextValue = sequences[i].first() - sequences[i + 1].first()
        sequences[i] = listOf(nextValue) + sequences[i]
    }

    return sequences.first().first()
}
