package day07

import java.io.File

fun main() {
    val lines = File("day07/input.txt").readLines()

    val cardOrder = listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2")
    val cardValues = parseCardValues(lines)
    val sortedCards = sortCards(cardValues, cardOrder)

    println("Final scores: ${calculateScores(sortedCards, cardValues).sum()}") // 250232501
}

fun parseCardValues(lines: List<String>): Map<String, Int> =
    lines.associate {
        val (card, value) = it.split(" ")
        card to value.toInt()
    }

fun sortCards(cardValues: Map<String, Int>, cardOrder: List<String>): List<String> =
    cardValues.keys.sortedWith { a, b ->
        compareCardStrength(a, b, cardOrder)
    }

fun compareCardStrength(card1: String, card2: String, cardOrder: List<String>): Int =
    calculateCardStrength(card2, cardOrder).compareTo(calculateCardStrength(card1, cardOrder))

fun calculateCardStrength(card: String, cardOrder: List<String>): Long {
    val handRank = handRanking(card)
    return handRank * 10000000000L +
            cardOrder.indexOf(card[0].toString()) * 100000000 +
            cardOrder.indexOf(card[1].toString()) * 1000000 +
            cardOrder.indexOf(card[2].toString()) * 10000 +
            cardOrder.indexOf(card[3].toString()) * 100 +
            cardOrder.indexOf(card[4].toString())
}

fun handRanking(card: String): Int {
    val cardCount = card.groupingBy { it }.eachCount()
    return when (cardCount.size) {
        1 -> 1
        2 -> when (cardCount.values.toSet()) {
            setOf(4, 1) -> 2
            setOf(3, 2) -> 3
            else -> throw IllegalArgumentException("Invalid card hand: $card")
        }

        3 -> when (cardCount.values.toSet()) {
            setOf(3, 1) -> 4
            setOf(2, 1) -> 5
            else -> throw IllegalArgumentException("Invalid card hand: $card")
        }

        4 -> 6
        5 -> 7
        else -> throw IllegalArgumentException("Invalid card hand: $card")
    }
}

fun calculateScores(cards: List<String>, cardValues: Map<String, Int>): List<Int> =
    cards.mapIndexed { index, card ->
        cardValues[card]!! * (index + 1)
    }
