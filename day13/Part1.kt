package day13

import java.io.File
import java.lang.Integer.min

fun main() {
    val groups = parseGroups(input = File("day13/input.txt").readLines())
    println("Total Summary: ${groups.sumOf { processGroup(it) }}") // 34821
}

fun parseGroups(input: List<String>): MutableList<List<String>> {
    val groups = mutableListOf<List<String>>()
    var currentGroup = mutableListOf<String>()

    for (line in input) {
        if (line.isBlank()) {
            if (currentGroup.isNotEmpty()) {
                groups.add(currentGroup)
                currentGroup = mutableListOf()
            }
        } else {
            currentGroup.add(line)
        }
    }
    if (currentGroup.isNotEmpty()) groups.add(currentGroup)
    return groups
}

fun processGroup(group: List<String>): Int {
    val verticalReflection = findVerticalReflection(group)
    return if (verticalReflection != -1) verticalReflection
    else 100 * findHorizontalReflection(group)
}

fun findVerticalReflection(group: List<String>): Int {
    val width = group[0].length
    for (mid in 0 until width - 1) {
        val expandSteps = min(mid, width - mid)
        val originLeft = getColumn(group, mid)
        val originRight = getColumn(group, mid + 1)

        if (originLeft != originRight) continue
        if (expandSteps == 0) return mid + 1

        for (offset in 1..expandSteps) {
            val leftIndex = mid - offset
            val rightIndex = mid + offset + 1

            if (leftIndex < 0 || rightIndex > width - 1) return mid + 1
            if (getColumn(group, leftIndex) != getColumn(group, rightIndex)) break
            if (offset == expandSteps) return mid + 1
        }
    }
    return -1
}

fun findHorizontalReflection(group: List<String>): Int {
    val height = group.size
    for (mid in 0 until height - 1) {
        val expandSteps = min(mid, height - mid)
        val originTop = group[mid]
        val originBottom = group[mid + 1]

        if (originTop != originBottom) continue
        if (expandSteps == 0) return mid + 1

        for (offset in 1..expandSteps) {
            val topIndex = mid + offset + 1
            val bottomIndex = mid - offset

            if (bottomIndex < 0 || topIndex > height - 1) return mid + 1
            if (group[topIndex] != group[bottomIndex]) break
            if (offset == expandSteps) return mid + 1
        }
    }
    return -1
}

fun getColumn(grid: List<String>, columnIndex: Int): String {
    val column = StringBuilder()
    for (row in grid) {
        column.append(row[columnIndex])
    }
    return column.toString()
}
