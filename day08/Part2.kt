package day08

fun main() {
    val (instructions, nodes) = readInputFromFile("day08/input.txt")

    val pathLengths = nodes.keys
        .filter { it.endsWith("A") }
        .map { calculatePathLength(nodes, it, instructions).toLong() }

    val result = pathLengths.reduce { acc, i -> lcm(acc, i) }

    println("Steps required: $result") // 16187743689077
}

fun calculatePathLength(nodes: Map<String, Pair<String, String>>, startNode: String, instructions: String): Int {
    var currentNode = startNode
    var steps = 0
    while (!currentNode.endsWith("Z")) {
        currentNode = navigate(nodes, currentNode, instructions[steps % instructions.length])
        steps++
    }
    return steps
}

fun navigate(nodes: Map<String, Pair<String, String>>, currentNode: String, instruction: Char): String {
    val (left, right) = nodes[currentNode]!!
    return if (instruction == 'L') left else right
}

fun lcm(a: Long, b: Long): Long {
    return a / gcd(a, b) * b
}

fun gcd(a: Long, b: Long): Long {
    return if (b == 0L) a else gcd(b, a % b)
}
