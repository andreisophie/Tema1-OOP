package game;

import cards.Deck;

import java.util.ArrayList;

public class Player {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<Deck> decks;

    public Player() {
        decks = new ArrayList<>();
    }

    public void addDeck(Deck deck) {
        decks.add(deck);
    }
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public ArrayList<Deck> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<Deck> decks) {
        this.decks = decks;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nrCardsInDeck=" + nrCardsInDeck +
                ", nrDecks=" + nrDecks +
                ", decks=" + decks +
                '}';
    }
}
