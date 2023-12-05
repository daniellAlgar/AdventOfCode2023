package day05

import java.io.File

data class Mapping(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long)

fun main() {
    val (seeds, mappings) = parseInput(fileName = "day05/input.txt")

    val finalLocations = seeds.map { seed ->
        var currentNumber = seed
        mappings.forEach { mappingList ->
            currentNumber = convertNumber(currentNumber, mappingList)
        }
        currentNumber
    }

    println("Lowest location number: ${finalLocations.minOrNull()}") // 551761867
}

private fun convertNumber(number: Long, mappings: List<Mapping>): Long {
    mappings.forEach { mapping ->
        if (number in mapping.sourceStart until (mapping.sourceStart + mapping.rangeLength)) {
            return mapping.destinationStart + (number - mapping.sourceStart)
        }
    }
    return number
}

private fun parseInput(fileName: String): Pair<List<Long>, List<List<Mapping>>> {
    val lines = File(fileName).readLines()
    val seeds = lines.first().removePrefix("seeds: ").split(" ").map { it.toLong() }

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

    return Pair(seeds, mappings)
}