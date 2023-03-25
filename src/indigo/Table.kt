package indigo

class Table(val playingDeck: Deck, val player1: Player, val player2: Player) {
    var cardOnTable = mutableListOf<Card>()
    var topCard:Card = Card("","")
    fun initCard() {
        print("Initial cards on the table: ")
        cardOnTable = playingDeck.getNCard(4)
        topCard=cardOnTable[3]
        println(cardOnTable.joinToString(" "))
    }

    fun getState() {
        if (cardOnTable.size == 0) {
            println("No cards on the table")
        } else {
            println(
                "${cardOnTable.size} " + "cards on the table, and the top card is ${cardOnTable[cardOnTable.lastIndex]}"
            )
        }
    }




}