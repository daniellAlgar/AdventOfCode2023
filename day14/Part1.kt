package day14

import java.io.File

fun main() {
    val lines = File("day14/input.txt").readLines()
    val platform = lines.map { it.toCharArray() }.toTypedArray()
    val numRows = platform.size
    val numCols = platform[0].size

    for (col in 0 until numCols) {
        tiltColumn(platform, col, numRows)
    }

    var totalLoad = 0
    for (row in 0 until numRows) {
        for (col in 0 until numCols) {
            if (platform[row][col] == 'O') {
                totalLoad += numRows - row
            }
        }
    }

    println("Total load on the north support beams: $totalLoad") // 105784
}

fun tiltColumn(platform: Array<CharArray>, col: Int, numRows: Int) {
    var firstFreeSpace = -1
    for (row in 0 until numRows) {
        if (platform[row][col] == '.' && firstFreeSpace == -1) {
            firstFreeSpace = row
        } else if (platform[row][col] == 'O' && firstFreeSpace != -1) {
            platform[row][col] = '.'
            platform[firstFreeSpace][col] = 'O'
            firstFreeSpace++
        } else if (platform[row][col] == '#') {
            firstFreeSpace = -1
        }
    }
}
