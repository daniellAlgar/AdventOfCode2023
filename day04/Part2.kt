package day04

import java.io.File

fun main() {
    val lines = File("day04/input.txt").readLines()

    val cards = lines.map { line ->
        val (winningNumbers, myNumbers) = parse(line)
        Pair(winningNumbers, myNumbers)
    }

    println("Total Scratchcards: ${processCards(cards)}") // 6189740
}

fun processCards(cards: List<Pair<Set<Int>, Set<Int>>>): Int {
    val queue = ArrayDeque<Pair<Set<Int>, Set<Int>>>()
    cards.forEach { queue.add(it) }

    var totalCards = 0
    while (queue.isNotEmpty()) {
        val currentCard = queue.removeFirst()
        totalCards++

        val matches = currentCard.first.intersect(currentCard.second).size
        for (i in 1..matches) {
            val nextCardIndex = cards.indexOf(currentCard) + i
            if (nextCardIndex < cards.size) {
                queue.add(cards[nextCardIndex])
            }
        }
    }

    return totalCards
}
