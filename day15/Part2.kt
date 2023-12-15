package day15

import java.io.File

data class Lens(val label: String, val focalLength: Int)

fun main() {
    val steps = File("day15/input.txt").readLines().first().split(',')
    val boxes = Array<MutableList<Lens>>(256) { mutableListOf() }

    for (step in steps) {
        val boxNumber = hash(step.filter { it.isLetter() })
        val operation = step.first { it == '-' || it == '=' }
        val label = step.filter { it.isLetter() }

        when (operation) {
            '-' -> boxes[boxNumber].removeIf { it.label == label }
            '=' -> {
                val focalLength = step.filter { it.isDigit() }.toInt()
                val existingLensIndex = boxes[boxNumber].indexOfFirst { it.label == label }
                if (existingLensIndex != -1) {
                    boxes[boxNumber][existingLensIndex] = Lens(label, focalLength)
                } else {
                    boxes[boxNumber].add(Lens(label, focalLength))
                }
            }
        }
    }

    println("Total Focusing Power: ${calculateFocusingPower(boxes)}") // 259356
}

fun calculateFocusingPower(boxes: Array<MutableList<Lens>>): Int {
    var totalPower = 0
    for (boxNumber in boxes.indices) {
        for (slotNumber in boxes[boxNumber].indices) {
            val lens = boxes[boxNumber][slotNumber]
            totalPower += (boxNumber + 1) * (slotNumber + 1) * lens.focalLength
        }
    }
    return totalPower
}