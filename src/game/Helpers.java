package game;

import cards.Card;
import cards.Deck;
import cards.environments.Firestorm;
import cards.environments.HeartHound;
import cards.environments.Winterfell;
import cards.minions.Minion;
import cards.minions.casterMinions.Disciple;
import cards.minions.casterMinions.Miraj;
import cards.minions.casterMinions.TheCursedOne;
import cards.minions.casterMinions.TheRipper;
import fileio.CardInput;
import fileio.Input;

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
            default -> null;
        };
    }

    static public void initializePlayers(Input inputData) {
        Deck deck;
        Card card;
        // initialize player 1
        Game.players[0] = new Player();
        Game.players[0].setNrDecks(inputData.getPlayerOneDecks().getNrDecks());
        Game.players[0].setNrCardsInDeck(inputData.getPlayerOneDecks().getNrCardsInDeck());
        for (int i = 0; i < Game.players[0].getNrDecks(); i++) {
            deck = new Deck();
            for (int j = 0; j < Game.players[0].getNrCardsInDeck(); j++) {
                CardInput cardInput = inputData.getPlayerOneDecks().getDecks().get(i).get(j);
                deck.cards.add(Helpers.CardInputToCard(cardInput));
            }
            Game.players[0].addDeck(deck);
        }
        // initialize player 2
        Game.players[1] = new Player();
        Game.players[1].setNrDecks(inputData.getPlayerOneDecks().getNrDecks());
        Game.players[1].setNrCardsInDeck(inputData.getPlayerOneDecks().getNrCardsInDeck());
        for (int i = 0; i < Game.players[1].getNrDecks(); i++) {
            deck = new Deck();
            for (int j = 0; j < Game.players[1].getNrCardsInDeck(); j++) {
                CardInput cardInput = inputData.getPlayerTwoDecks().getDecks().get(i).get(j);
                deck.cards.add(Helpers.CardInputToCard(cardInput));
            }
            Game.players[1].addDeck(deck);
        }

        Game.setInitialised(true);
    }
}
