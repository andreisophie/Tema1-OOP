package cards;

import cards.environments.Environment;
import cards.minions.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import helpers.Helpers;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
    }

    public Deck(Deck deck) {
        cards = new ArrayList<Card>();

        for (Card card : deck.getCards()) {
            if (card instanceof Minion) {
                cards.add(card.cloneCard());
            } else {
                cards.add(card);
            }

        }
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

    public ArrayNode toJSON() {
        ArrayNode deckArray = Helpers.mapper.createArrayNode();

        for (Card card : cards) {
            deckArray.add(card.toJSON());
        }

        return deckArray;
    }
}
