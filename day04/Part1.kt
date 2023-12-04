package day04

import java.io.File
import kotlin.math.pow

fun main() {
    val lines = File("day04/input.txt").readLines()

    val cards = lines.map { line ->
        val (winningNumbers, myNumbers) = line.substringAfter(": ")
            .split("|")
            .map { it.trim().split(" ").filter { num -> num.isNotBlank() }.map { num -> num.toInt() }.toSet() }

        calculatePointsForCard(winningNumbers, myNumbers)
    }

    val totalPoints = cards.sum()
    println("Total Points: $totalPoints") // 15205
}

fun calculatePointsForCard(winningNumbers: Set<Int>, myNumbers: Set<Int>): Int {
    val matches = winningNumbers.intersect(myNumbers)
    return if (matches.isNotEmpty()) 2.0.pow(matches.size - 1).toInt() else 0
}
