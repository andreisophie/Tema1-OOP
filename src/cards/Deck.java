package cards;

import cards.minions.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import helpers.Helpers;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(final Deck deck) {
        cards = new ArrayList<Card>();

        for (Card card : deck.getCards()) {
            if (card instanceof Minion) {
                cards.add(((Minion) card).cloneMinion());
            } else {
                cards.add(card);
            }

        }
    }

    /**
     *
     * @return the Array of Cards in current instance
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     *
     * @return current Deck as Array of Cards in String format
     */
    @Override
    public String toString() {
        StringBuilder retString = new StringBuilder("[");

        for (Card card : cards) {
            retString.append(card.toString());
        }

        retString.append("]");

        return retString.toString();
    }

    /**
     *
     * @return an ArrayNode of Cards in JSON format,
     * to be used for output
     */
    public ArrayNode toJSON() {
        ArrayNode deckArray = Helpers.mapper.createArrayNode();

        for (Card card : cards) {
            deckArray.add(card.toJSON());
        }

        return deckArray;
    }
}
