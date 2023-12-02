package Day02

import java.io.File

fun main() {
    val lines = File("Day02/input.txt").readLines()

    val games = lines.map { parseGame(it) }
    val powers = games.map { calculatePower(it) }

    println("Powers: $powers")
    println("Sum of Powers: ${powers.sum()}") // 83707
}

fun calculatePower(game: Pair<Int, List<Map<String, Int>>>): Int {
    val sets = game.second
    val maxRed = sets.maxOfOrNull { it["red"] ?: 0 } ?: 0
    val maxGreen = sets.maxOfOrNull { it["green"] ?: 0 } ?: 0
    val maxBlue = sets.maxOfOrNull { it["blue"] ?: 0 } ?: 0

    return maxRed * maxGreen * maxBlue
}
