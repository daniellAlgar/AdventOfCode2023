import java.io.File

fun main() {
    val lines = File("Day01/input.txt").readLines()
    println("Day01_2: ${lines.sumOf { calibrationValue(it) }}") // 54845
}

private fun calibrationValue(input: String): Int {
    val numberMap = mapOf(
            "one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5,
            "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9
    )
    val regex = Regex("(?=(one|two|three|four|five|six|seven|eight|nine))|\\d")

    val matches = regex.findAll(input)
            .map { it.groups[1]?.value ?: it.value }
            .map { numberMap[it] ?: it.toInt() }

    return matches.first() * 10 + matches.last()
}
