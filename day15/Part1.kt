package day15

import java.io.File

fun main() {
    val steps = File("day15/input.txt").readLines().first().split(',')
    println("Sum of HASH values: ${steps.sumOf { hash(it) }}") // 497373
}

fun hash(step: String): Int {
    var currentValue = 0
    for (char in step) {
        val ascii = char.code
        currentValue += ascii
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue
}
