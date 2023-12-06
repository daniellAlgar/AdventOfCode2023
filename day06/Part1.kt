package day06

import java.io.File

fun main() {
    val lines = File("day06/input.txt").readLines()

    val raceTimes = lines[0].split("\\s+".toRegex()).drop(1).map { it.toInt() }
    val recordDistances = lines[1].split("\\s+".toRegex()).drop(1).map { it.toInt() }

    val waysToWin = raceTimes.zip(recordDistances).map { (time, record) ->
        countWaysToBeatRecord(time.toLong(), record.toLong())
    }

    println("Ways to beet the record: ${waysToWin.fold(1L) { acc, ways -> acc * ways }}") // 138915
}

fun countWaysToBeatRecord(time: Long, record: Long): Long {
    var count = 0L
    for (holdTime in 0 until time) {
        val travelTime = time - holdTime
        val distance = holdTime * travelTime
        if (distance > record) count++
    }
    return count
}

