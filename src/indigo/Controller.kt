package indigo

import java.util.*

class Controller {
    val scanner = Scanner(System.`in`)
    val player1 = Player(name = "Player")
    val player2 = Player(name = "Computer")
    val deck = Deck().getNewDeck().shuffleDeck()
    val table = Table(deck, player1, player2)
    var whoseTurn = ""
    var whoFirst = ""
    var lastWon = ""
    val strategy = Strategy(player2, table)
    fun startGame() {

        println("Indigo Card Game")

        do {
            println("Play first?")
            when (scanner.nextLine().lowercase()) {
                "yes" -> {
                    whoFirst = "player1"
                    whoseTurn = "player1"
                    break
                }

                "no" -> {
                    whoFirst = "player2"
                    whoseTurn = "player2"
                    break
                }
            }
        } while (true)

        table.initCard()
        player1.drawNCard(6, deck)
        player2.drawNCard(6, deck)


        while (true) {
            table.getState()
            when (whoseTurn) {
                "player1" -> {
                    if (!takePlayerTurn()) {
                        println("Game Over")
                        break
                    }
//                    whoseTurn = "player2"
                }

                "player2" -> {
                    takeComputerTurn()
//                    whoseTurn = "player1"
                }
            }

            if (checkGameOver()) {
                table.getState()
                gameOverHandle()
                println("Game Over")
                break
            }
            whoseTurn = if (whoseTurn == "player1") "player2" else "player1"


        }

    }

    private fun gameOverHandle() {
        //The remaining cards on the table go to the player who won the cards last
        when (lastWon) {
            player1.name -> {
                player1.cardsWon += calCard()
                player1.score += calScore()
            }

            player2.name -> {
                player2.cardsWon += calCard()
                player2.score += calScore()
            }
        }
        when {
            player1.cardsWon.size > player2.cardsWon.size -> {
                player1.score += 3
            }

            player1.cardsWon.size < player2.cardsWon.size -> {
                player2.score += 3
            }

            else -> {
                when (whoFirst) {
                    "player1" -> player1.score += 3
                    "player2" -> player2.score += 3
                }
            }
        }

        println("Score: ${player1.name} ${player1.score} - ${player2.name} ${player2.score}")
        println("Cards: ${player1.name} ${player1.cardsWon.size} - ${player2.name} ${player2.cardsWon.size}")
    }

    private fun checkGameOver(): Boolean {
        if (table.playingDeck.deck.size + player1.getNumCardInHand() + player2.getNumCardInHand() == 0) {
            return true
        }
        return false

    }

    private fun takeComputerTurn() {
        if (player2.getNumCardInHand() == 0) {
            player2.drawNCard(6, deck)
        }
        println(player2.getCardInHand())
        val chosenCard = player2.autoPlayCard(strategy)
        table.cardOnTable.add(chosenCard)
        println("Computer plays $chosenCard")
        println()
        scoreHandle(isMatchedTopCard(chosenCard), player2)
//        table.topCard = chosenCard

    }

    //Return true if play chose a card, false if exit
    private fun takePlayerTurn(): Boolean {
        if (player1.getNumCardInHand() == 0) {
            player1.drawNCard(6, deck)
        }
        player1.getInfoCardInHand()
        val chosenCard = player1.chooseCard()
        return if (chosenCard != null) {
            player1.playCard(chosenCard)
            println()
            table.cardOnTable.add(chosenCard)
            scoreHandle(isMatchedTopCard(chosenCard), player1)
//            table.topCard = chosenCard

            true
        } else {
            false
        }
    }

    private fun scoreHandle(isMatchedTopCard: Boolean, player: Player) {
        if (isMatchedTopCard) {
            player.cardsWon += calCard()
            player.score += calScore()
            table.cardOnTable.clear()
            lastWon = player.name
            println("${player.name} wins cards")
            println("Score: ${player1.name} ${player1.score} - ${player2.name} ${player2.score}")
            println("Cards: ${player1.name} ${player1.cardsWon.size} - ${player2.name} ${player2.cardsWon.size}")
            println()
        }
    }

    private fun calCard(): MutableList<Card> {
        return table.cardOnTable
    }

    private fun calScore(): Int {
        var sum = 0
        for (card in table.cardOnTable) {
            sum += when (card.rank) {
                "A", "10", "J", "Q", "K" -> 1
                else -> 0
            }
        }
        return sum
    }

    private fun isMatchedTopCard(chosenCard: Card): Boolean {
        //1 because the card have just been played
//        if (table.cardOnTable.size == 1) {
//            return false
//        }
        //-1 because exclude the card that player have just played
//        val topCard = table.cardOnTable[table.cardOnTable.lastIndex - 1]
        return if (table.topCard.rank == chosenCard.rank || table.topCard.suit == chosenCard.suit) {
            table.topCard = Card("", "")
            true
        } else {
            table.topCard = chosenCard
            false
        }
    }

}