package day06

import java.io.File

fun main() {
    val lines = File("day06/input.txt").readLines()
    val raceTime = lines[0].split(":")[1].replace(" ", "").toLong()
    val recordDistance = lines[1].split(":")[1].replace(" ", "").toLong()
    println("Ways to beet the record: ${countWaysToBeatRecord(raceTime, recordDistance)}") // 27340847
}
