package game;

import cards.Card;
import cards.Deck;
import cards.heroes.Hero;
import cards.minions.Minion;
import fileio.CardInput;
import fileio.Input;
import fileio.StartGameInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    static public ArrayList<Minion>[] playground = new ArrayList[4];
    static public Player player1, player2;
    static public int currentPlayer;

    public static Player getPlayerByIndex(int index) {
        if (index == 1)
            return player1;
        return player2;
    }

    static public void initializePlayers(Input inputData) {
        Deck deck;
        Card card;
        // initialize player 1
        player1 = new Player();
        player1.setNrDecks(inputData.getPlayerOneDecks().getNrDecks());
        player1.setNrCardsInDeck(inputData.getPlayerOneDecks().getNrCardsInDeck());
        for (int i = 0; i < player1.getNrDecks(); i++) {
            deck = new Deck();
            for (int j = 0; j < player1.getNrCardsInDeck(); j++) {
                CardInput cardInput = inputData.getPlayerOneDecks().getDecks().get(i).get(j);
                deck.getCards().add(Helpers.CardInputToCard(cardInput));
            }
            player1.addDeck(deck);
        }
        player1.setHand(new Deck());
        // initialize player 2
        player2 = new Player();
        player2.setNrDecks(inputData.getPlayerOneDecks().getNrDecks());
        player2.setNrCardsInDeck(inputData.getPlayerOneDecks().getNrCardsInDeck());
        for (int i = 0; i < player2.getNrDecks(); i++) {
            deck = new Deck();
            for (int j = 0; j < player2.getNrCardsInDeck(); j++) {
                CardInput cardInput = inputData.getPlayerTwoDecks().getDecks().get(i).get(j);
                deck.getCards().add(Helpers.CardInputToCard(cardInput));
            }
            player2.addDeck(deck);
        }
        player2.setHand(new Deck());
    }

    static public void initializeGame(StartGameInput startGameInput) {
        player1.setHero((Hero) Helpers.CardInputToCard(startGameInput.getPlayerOneHero()));
        player2.setHero((Hero) Helpers.CardInputToCard(startGameInput.getPlayerTwoHero()));

        player1.setCurrentDeck(new Deck(player1.getDecks().get(startGameInput.getPlayerOneDeckIdx())));
        player2.setCurrentDeck(new Deck(player2.getDecks().get(startGameInput.getPlayerTwoDeckIdx())));

        Random random = new Random(startGameInput.getShuffleSeed());
        Collections.shuffle(player1.getCurrentDeck().getCards());
        random = new Random(startGameInput.getShuffleSeed());
        Collections.shuffle(player2.getCurrentDeck().getCards());

        currentPlayer = startGameInput.getStartingPlayer();

        player1.getHand().getCards().add(player1.getCurrentDeck().getCards().remove(0));
        player2.getHand().getCards().add(player2.getCurrentDeck().getCards().remove(0));
    }
}
