package indigo

class Strategy(val computer: Player, val table: Table) {

    val mapCandidateCards = mutableMapOf<Card, Int>()

    fun chooseCard(): Card {
        var chosenCard = Card("", "")
        mapCandidateCards.clear()
        mapCandidateCards.putAll(listCandidateCards())
        if (computer.cardInHand.size == 1) {
            return handleOneCardInHand()

        }
        if (mapCandidateCards.size == 1) {
            return handleOneCandidateCardInHand()

        }

        if (table.cardOnTable.size == 0) {
            return handleNoCardOnTheTable()
        }

        if (table.cardOnTable.size != 0 && mapCandidateCards.isEmpty()) {
            return handleHasCardOnTableAndNoCandidateCard()
        }

        if (mapCandidateCards.size >= 2) {
            return handleTwoMoreCandidateCards() as Card
        }

        return chosenCard
    }

    private fun handleOneCardInHand(): Card {
        return computer.cardInHand.removeAt(0)
    }

    private fun handleOneCandidateCardInHand(): Card {
        computer.cardInHand.remove(mapCandidateCards.keys.first())
        return mapCandidateCards.keys.first()
    }

    private fun handleNoCardOnTheTable(): Card {
        val card= tryPlaySameSuit() ?: tryPlaySameRank() ?: playRandom()
        computer.cardInHand.remove(card)
        return card
    }

    private fun playRandom(): Card {
        return computer.cardInHand[0]
    }

    private fun tryPlaySameSuit(): Card? {
//        println("Top card ${table.topCard}" )
        val listSuit = mutableListOf<String>()

        for (card in computer.cardInHand) {
            if (listSuit.contains(card.suit)) {
//                computer.cardInHand.remove(card)
                return card
            } else {
                listSuit.add(card.suit)
            }
        }
        return null

    }

    private fun tryPlaySameRank(): Card? {
        val listRank = mutableListOf<String>()
        for (card in computer.cardInHand) {
            if (listRank.contains(card.rank)) {
//                computer.cardInHand.remove(card)
                return card
            } else{
                listRank.add(card.rank)
            }
        }
        return null
    }

    private fun handleHasCardOnTableAndNoCandidateCard(): Card {
        return handleNoCardOnTheTable()
    }

    private fun handleTwoMoreCandidateCards(): Card? {
        if (mapCandidateCards.containsValue(2)) {

            computer.cardInHand.remove(
                mapCandidateCards.entries.find { it.value == 2 }?.key
            )
            return mapCandidateCards.entries.find { it.value == 2 }?.key
        } else if (mapCandidateCards.containsValue(1)) {
            computer.cardInHand.remove(
                mapCandidateCards.entries.find { it.value == 1 }?.key
            )
            return mapCandidateCards.entries.find { it.value == 1 }?.key

        } else {
            computer.cardInHand.remove(mapCandidateCards.keys.first())
            return mapCandidateCards.keys.first()
        }
    }


    private fun listCandidateCards(): MutableMap<Card, Int> {
        val mapCards = mutableMapOf<Card, Int>()
        for (card in computer.cardInHand) {
//            if (card.suit == table.topCard.suit && card.rank == table.topCard.rank) {
//                mapCards[card] = 3
//                continue
//            }
            if (card.suit == table.topCard.suit) {
                mapCards[card] = 2
                continue
            }
            if (card.rank == table.topCard.rank) {
                mapCards[card] = 1
            }
        }
        return mapCards
    }
}
