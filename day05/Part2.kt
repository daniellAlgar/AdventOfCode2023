package day05

import java.io.File

fun main() {
    val (seedRanges, mappings) = parseInput(fileName = "day05/input.txt")

    var lowestLocation: Long? = null

    for ((start, length) in seedRanges) {
        for (seed in start until start + length) {
            var currentNumber = seed
            mappings.forEach { mappingList ->
                currentNumber = convertNumber(currentNumber, mappingList)
            }
            lowestLocation = if (lowestLocation == null || currentNumber < lowestLocation) {
                currentNumber
            } else {
                lowestLocation
            }
        }
    }

    println("Lowest location number: $lowestLocation") // 57451709
}

private fun parseInput(fileName: String): Pair<List<Pair<Long, Long>>, List<List<Mapping>>> {
    val lines = File(fileName).readLines()

    val seedRanges = lines
        .first()
        .removePrefix("seeds: ")
        .split(" ")
        .windowed(2, 2)
        .map { (start, length) -> Pair(start.toLong(), length.toLong()) }

    val mappings = mutableListOf<List<Mapping>>()
    var currentMappings = mutableListOf<Mapping>()

    for (line in lines.drop(1)) {
        if (line.endsWith("map:")) {
            if (currentMappings.isNotEmpty()) {
                mappings.add(currentMappings)
                currentMappings = mutableListOf()
            }
        } else if (line.isNotBlank()) {
            val parts = line.split(" ").map { it.toLong() }
            currentMappings.add(Mapping(parts[0], parts[1], parts[2]))
        }
    }
    if (currentMappings.isNotEmpty()) {
        mappings.add(currentMappings)
    }

    return Pair(seedRanges, mappings)
}