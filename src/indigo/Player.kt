package indigo

class Player(val name:String = "Player") {
    val cardInHand = mutableListOf<Card>()
    var score:Int =0
    var cardsWon= mutableListOf<Card>()

    fun getNumCardInHand(): Int {
        return cardInHand.size
    }

    fun getCardInHand():String{
        var rs=""
        for (card in cardInHand){
            rs+= "$card "
        }
        return rs
    }

    fun getInfoCardInHand() {
        print("Cards in hand: ")
        for (index in cardInHand.indices) {
            print("${index + 1})${cardInHand[index]} ")
        }
        println()
    }

    fun drawCard(deck: Deck){
        cardInHand.add(deck.draw())
    }

    fun drawNCard(n:Int,deck: Deck){
        repeat(n){
            drawCard(deck)
        }
    }



    fun chooseCard(): Card? {
        while (true) {
            println("Choose a card to play (1-${cardInHand.size}):")
            try {
                val input: String = readln()
                if (input == "exit") {
                    return null
                } else {
                    val whichCard = input.toInt()
                    if (whichCard in 1..cardInHand.size) {
                        return cardInHand[whichCard - 1]
                    }
                }
            } catch (_: Exception) {

            }
        }
    }
    fun playCard(card: Card) {
        cardInHand.remove(card)
    }

    fun autoPlayCard(strategy: Strategy): Card {
      return  strategy.chooseCard()

//        return cardInHand.removeAt(0)
    }


}