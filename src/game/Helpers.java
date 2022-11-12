package game;

import cards.Card;
import cards.Deck;
import cards.environments.Firestorm;
import cards.environments.HeartHound;
import cards.environments.Winterfell;
import cards.heroes.*;
import cards.minions.Minion;
import cards.minions.casterMinions.Disciple;
import cards.minions.casterMinions.Miraj;
import cards.minions.casterMinions.TheCursedOne;
import cards.minions.casterMinions.TheRipper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;

import java.util.Collections;
import java.util.Random;

public class Helpers {
    static public Card CardInputToCard(CardInput cardInput) {
        return switch (cardInput.getName()) {
            case "Sentinel", "Berserker" -> new Minion(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    0,
                    false);
            case "Goliath", "Warden" -> new Minion(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    1,
                    true);
            case "The Ripper" -> new TheRipper(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    1);
            case "Miraj" -> new Miraj(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    1);
            case "The Cursed One" -> new TheCursedOne(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    0);
            case "Disciple" -> new Disciple(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    0);
            case "Firestorm" -> new Firestorm(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "Winterfell" -> new Winterfell(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "Heart Hound" -> new HeartHound(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "Lord Royce" -> new LordRoyce(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "Empress Thorina" -> new EmpressThorina(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "King Mudface" -> new KingMudface(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "General Kocioraw" -> new GeneralKocioraw(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            default -> null;
        };
    }

    static public void initializePlayers(Input inputData) {
        Deck deck;
        Card card;
        // initialize player 1
        Game.player1 = new Player();
        Game.player1.setNrDecks(inputData.getPlayerOneDecks().getNrDecks());
        Game.player1.setNrCardsInDeck(inputData.getPlayerOneDecks().getNrCardsInDeck());
        for (int i = 0; i < Game.player1.getNrDecks(); i++) {
            deck = new Deck();
            for (int j = 0; j < Game.player1.getNrCardsInDeck(); j++) {
                CardInput cardInput = inputData.getPlayerOneDecks().getDecks().get(i).get(j);
                deck.getCards().add(Helpers.CardInputToCard(cardInput));
            }
            Game.player1.addDeck(deck);
        }
        // initialize player 2
        Game.player2 = new Player();
        Game.player2.setNrDecks(inputData.getPlayerOneDecks().getNrDecks());
        Game.player2.setNrCardsInDeck(inputData.getPlayerOneDecks().getNrCardsInDeck());
        for (int i = 0; i < Game.player2.getNrDecks(); i++) {
            deck = new Deck();
            for (int j = 0; j < Game.player2.getNrCardsInDeck(); j++) {
                CardInput cardInput = inputData.getPlayerTwoDecks().getDecks().get(i).get(j);
                deck.getCards().add(Helpers.CardInputToCard(cardInput));
            }
            Game.player2.addDeck(deck);
        }
    }

    static public void initializeGame(StartGameInput startGameInput) {
        Game.player1.setHero((Hero) CardInputToCard(startGameInput.getPlayerOneHero()));
        Game.player2.setHero((Hero) CardInputToCard(startGameInput.getPlayerTwoHero()));

        Game.player1.setCurrentDeck(new Deck(Game.player1.getDecks().get(startGameInput.getPlayerOneDeckIdx())));
        Game.player2.setCurrentDeck(new Deck(Game.player2.getDecks().get(startGameInput.getPlayerTwoDeckIdx())));

        Random random = new Random(startGameInput.getShuffleSeed());
        Collections.shuffle(Game.player1.getCurrentDeck().getCards());
        Collections.shuffle(Game.player2.getCurrentDeck().getCards());

        Game.currentPlayer = startGameInput.getStartingPlayer();
    }

    static public void runAction(ActionsInput actionInput, ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("command", actionInput.getCommand());
        switch (actionInput.getCommand()) {
            case "getPlayerDeck":
                result.put("playerIdx", actionInput.getPlayerIdx());

                System.out.println(Game.getPlayerByIndex(actionInput.getPlayerIdx()).getCurrentDeck().toString());
                break;
            case "getPlayerHero":
                result.put("playerIdx", actionInput.getPlayerIdx());
                System.out.println(Game.getPlayerByIndex(actionInput.getPlayerIdx()).getHero().toString());
                break;
            case "getCardsInHand":
                result.put("playerIdx", actionInput.getPlayerIdx());
                System.out.println(Game.getPlayerByIndex(actionInput.getPlayerIdx()).getHand().toString());
                break;
            case "getPlayerTurn":
                result.put("output", Game.currentPlayer);
                System.out.println(Game.currentPlayer);
                break;
            default:
                System.out.println("Action not recognised or implemented");
        }

        output.add(result);
    }
}
