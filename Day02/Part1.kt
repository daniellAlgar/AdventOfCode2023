package Day02

import java.io.File

fun main() {
    val lines = File("Day02/input.txt").readLines()

    val games = lines.map { parseGame(it) }
    val possibleGames: List<Pair<Int, List<Map<String, Int>>>> =
        games.filter { isGamePossible(it, redCubes = 12, greenCubes = 13, blueCubes = 14) }

    println("Possible games: ${possibleGames.joinToString(", ") { it.first.toString() }}")
    println("Sum of IDs: ${possibleGames.sumOf { it.first }}") // 2685
}

fun parseGame(line: String): Pair<Int, List<Map<String, Int>>> {
    val parts = line.split(":")
    val gameId = (Regex("\\d+")).find(parts[0])!!.value.toInt()
    val sets = parts[1].split(";")
        .map { set ->
            set.split(",")
                .map { it.trim().split(" ") }
                .associate { it[1] to it[0].toInt() }
        }

    return gameId to sets
}

fun isGamePossible(game: Pair<Int, List<Map<String, Int>>>, redCubes: Int, greenCubes: Int, blueCubes: Int): Boolean {
    return game.second.all { set ->
        val red = set["red"] ?: 0
        val green = set["green"] ?: 0
        val blue = set["blue"] ?: 0

        red <= redCubes && green <= greenCubes && blue <= blueCubes
    }
}