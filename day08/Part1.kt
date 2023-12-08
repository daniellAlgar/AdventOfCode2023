package day08

import java.io.File

fun main() {
    val (instructions, nodeMap) = readInputFromFile("day08/input.txt")
    println("Steps to reach ZZZ: ${navigateToZZZ(nodeMap, instructions)}") // 14257
}

fun readInputFromFile(filePath: String): Pair<String, Map<String, Pair<String, String>>> {
    val lines = File(filePath).readLines()
    val instructions = lines.first()
    val nodeLines = lines.drop(2)

    val nodeMap = nodeLines.associate { line ->
        val (node, connections) = line.split(" = ")
        val (left, right) = connections.removeSurrounding("(", ")").split(", ")
        node to (left to right)
    }

    return instructions to nodeMap
}

fun navigateToZZZ(nodeMap: Map<String, Pair<String, String>>, instructions: String): Int {
    var currentNode = "AAA"
    var steps = 0

    while (currentNode != "ZZZ") {
        val (left, right) = nodeMap[currentNode] ?: break
        val nextInstruction = instructions[steps % instructions.length]
        currentNode = if (nextInstruction == 'L') left else right
        steps++
    }

    return steps
}
