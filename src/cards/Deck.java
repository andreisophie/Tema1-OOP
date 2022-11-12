package cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(Deck deck) {
        cards = new ArrayList<Card>(deck.getCards());
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        StringBuilder retString = new StringBuilder("[");

        for (Card card : cards) {
            retString.append(card.toString());
        }

        retString.append("]");

        return retString.toString();
    }

//    public String toJSON() {
//        ObjectMapper mapper = new ObjectMapper();
//        ArrayNode output = mapper.createArrayNode();
//
//        for (Card card : cards) {
//
//        }
//
//        ObjectNode result = JsonNodeFactory.instance.objectNode();
//    }
}
