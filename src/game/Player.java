package game;

import cards.Deck;
import cards.heroes.Hero;

import java.util.ArrayList;

public class Player {
    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<Deck> decks;
    private Hero hero;
    private Deck currentDeck;
    private Deck hand;
    private int mana = 0;

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

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    public Deck getHand() {
        return hand;
    }

    public void setHand(Deck hand) {
        this.hand = hand;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
}
