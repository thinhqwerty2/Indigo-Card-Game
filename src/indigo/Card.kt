package indigo

class Card(
    var rank: String, var suit: String
) {

    constructor(str:String) : this(str.substring(0,1),str.substring(1,2)){

    }

    override fun toString(): String {
        return rank + suit
    }
}