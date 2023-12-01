import java.io.File

fun main() {
    val lines = File("Day01/input.txt").readLines()
    println("Day01_1: ${lines.sumOf { concatFirstAndLastDigit(it) }}") // 55090
}

private fun concatFirstAndLastDigit(input: String): Int {
    val digits = Regex("\\d").findAll(input).map { it.value[0] }.toList()
    return "${digits.firstOrNull()}${digits.lastOrNull()}".toInt()
}
