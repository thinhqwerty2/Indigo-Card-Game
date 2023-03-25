package indigo

import java.util.*


class Deck {
    private val cardRanks = listOf<String>("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
    private val cardSuits = listOf<String>("♦", "♥", "♠", "♣")
    val deck = Stack<Card>()


    fun getNewDeck(): Deck {
        for (cardRank in cardRanks) {
            for (cardSuit in cardSuits) {
                deck.push(Card(cardRank,cardSuit))
            }
        }
        return this
    }

    fun resetDeck(): Deck {
        deck.clear()
        return this.getNewDeck()
    }

    fun shuffleDeck(): Deck {
        deck.shuffle()
        return this
    }

    fun draw(): Card {
        return deck.pop()
    }

    fun getNCard(n: Int = 1): MutableList<Card> {
        var tempCardDrawn = mutableListOf<Card>()
        when {
            n !in 1..52 -> println("Invalid number of cards.")
            n > deck.size -> println("The remaining cards are insufficient to meet the request.")
            else -> {
                repeat(n) {
                    tempCardDrawn.add(draw())
                }
            }
        }
        return tempCardDrawn
    }


}