import java.io.File

fun main() {
    val lines = File("input01_1.txt").readLines()
    println("Day01_1: ${lines.sumOf { concatFirstAndLastDigit(it) }}")
}

fun concatFirstAndLastDigit(input: String): Int {
    val digits = Regex("\\d").findAll(input).map { it.value[0] }.toList()
    return "${digits.firstOrNull()}${digits.lastOrNull()}".toInt()
}
