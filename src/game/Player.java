package game;

import cards.Deck;
import cards.heroes.Hero;

import java.util.ArrayList;

public class Player {
    private final int nrDecks;
    private final int nrCardsInDeck;
    private ArrayList<Deck> decks;
    private Hero hero;
    private Deck currentDeck;
    private Deck hand;
    private int mana = 0;
    private int numberWins = 0;

    public Player(final int nrDecks, final int nrCardsInDeck) {
        decks = new ArrayList<>();
        this.nrCardsInDeck = nrCardsInDeck;
        this.nrDecks = nrDecks;
    }

    /**
     * adds a deck to a player's list of available decks
     * @param deck deck to be added to list of available decks
     */
    public void addDeck(final Deck deck) {
        decks.add(deck);
    }

    /**
     * Returns the number of cards in a deck owned by this Player instance
     * @return number of card in a deck
     */
    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    /**
     *
     * @return number of decks owned by this player instance
     */
    public int getNrDecks() {
        return nrDecks;
    }

    /**
     *
     * @return decks owned by this player instance
     */
    public ArrayList<Deck> getDecks() {
        return decks;
    }

    /**
     *
     * @return number of games won by this player instance
     */
    public int getNumberWins() {
        return numberWins;
    }

    /**
     * increase number of games won by this player instance
     */
    public void incrementWins() {
        this.numberWins++;
    }

    /**
     *
     * @return active hero of this player instance
     */
    public Hero getHero() {
        return hero;
    }

    /**
     * sets the active hero of this player instance
     * @param hero Hero object to be set as active
     */
    public void setHero(final Hero hero) {
        this.hero = hero;
    }

    /**
     *
     * @return deck used by this player instance is current game
     */
    public Deck getCurrentDeck() {
        return currentDeck;
    }

    /**
     * sets the deck used by this player instance in current game
     * @param currentDeck deck to be set as active
     */
    public void setCurrentDeck(final Deck currentDeck) {
        this.currentDeck = currentDeck;
    }

    /**
     *
     * @return cards in hand as Deck object
     */
    public Deck getHand() {
        return hand;
    }

    /**
     * sets hand of this player instance
     * @param hand new deck to be set in hand
     */
    public void setHand(final Deck hand) {
        this.hand = hand;
    }

    /**
     *
     * @return mana currently owned by this player instance
     */
    public int getMana() {
        return mana;
    }

    /**
     * sets mana of this player instance
     * @param mana new mana value
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }
}
