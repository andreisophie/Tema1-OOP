package game;

import cards.Card;
import cards.Deck;
import cards.environments.Environment;
import cards.environments.HeartHound;
import cards.heroes.EmpressThorina;
import cards.heroes.Hero;
import cards.heroes.LordRoyce;
import cards.minions.Caster;
import cards.minions.Minion;
import cards.minions.casterMinions.Disciple;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.Input;
import fileio.StartGameInput;
import helpers.Debug;
import helpers.Helpers;
import helpers.Statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {
    static public ArrayList<Minion>[] playground = new ArrayList[4];
    static public Player player1, player2;
    static public int currentPlayer;
    static public int startingPlayer;
    static public int turnNumber;

    public static Player getPlayerByIndex(int index) {
        if (index == 1)
            return player1;
        return player2;
    }

    public static Player getCurrentPlayer() {
        return getPlayerByIndex(currentPlayer);
    }

    public static Player getEnemyPlayer() {
        return getPlayerByIndex(1 + currentPlayer % 2);
    }

    public static int getRowForMinion(Minion minion) {
        if (currentPlayer == 1) {
            if (minion.getPrefRow() == 0) {
                return 3;
            } else {
                return 2;
            }
        } else {
            if (minion.getPrefRow() == 0) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    static public void initializePlayers(Input inputData) {
        Deck deck;
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
        Collections.shuffle(player1.getCurrentDeck().getCards(), random);
        random = new Random(startGameInput.getShuffleSeed());
        Collections.shuffle(player2.getCurrentDeck().getCards(), random);

        startingPlayer = startGameInput.getStartingPlayer();
        currentPlayer = startingPlayer;
        turnNumber = 1;
        player1.setMana(1);
        player2.setMana(1);

        for (int i = 0; i < 4; i++) {
            playground[i] = new ArrayList<>();
        }

        player1.getHand().getCards().add(player1.getCurrentDeck().getCards().remove(0));
        player2.getHand().getCards().add(player2.getCurrentDeck().getCards().remove(0));
    }

    static public void cleanupGame() {
        for (int i = 0; i < 4; i++) {
            playground[i].clear();
        }

        player1.getHand().getCards().clear();
        player2.getHand().getCards().clear();

        player1.getCurrentDeck().getCards().clear();
        player2.getCurrentDeck().getCards().clear();

        player1.setHero(null);
        player2.setHero(null);
    }

    static public void endTurn() {
        Helpers.unfreezeMinions(currentPlayer);

        currentPlayer = 1 + currentPlayer % 2;

        if (currentPlayer == startingPlayer) {
            turnNumber++;
            player1.setMana(player1.getMana() + (Math.min(turnNumber, 10)));
            player2.setMana(player2.getMana() + (Math.min(turnNumber, 10)));
            if (player1.getCurrentDeck().getCards().size() > 0) {
                player1.getHand().getCards().add(player1.getCurrentDeck().getCards().remove(0));
            }
            if (player2.getCurrentDeck().getCards().size() > 0) {
                player2.getHand().getCards().add(player2.getCurrentDeck().getCards().remove(0));
            }
        }
    }

    static public String placeCard(int handIndex) {
        Card card = Game.getCurrentPlayer().getHand().getCards().get(handIndex);

        if (card instanceof Environment) {
            return "Cannot place environment card on table.";
        }

        if (card.getMana() > Game.getCurrentPlayer().getMana()) {
            return "Not enough mana to place card on table.";
        }

        if (Game.playground[getRowForMinion((Minion) card)].size() == 5) {
            return "Cannot place card on table since row is full.";
        }

        Game.getCurrentPlayer().setMana(Game.getCurrentPlayer().getMana() - card.getMana());
        Game.getCurrentPlayer().getHand().getCards().remove(card);
        Game.playground[getRowForMinion((Minion) card)].add((Minion) card);

        return null;
    }

    static public String useEnvironmentCard(int handIndex, int targetRow) {
        Card card = Game.getCurrentPlayer().getHand().getCards().get(handIndex);

        if (!(card instanceof  Environment)) {
            return "Chosen card is not of type environment.";
        }

        if (card.getMana() > Game.getCurrentPlayer().getMana()) {
            return "Not enough mana to use environment card.";
        }

        if (!Helpers.rowBelongsToEnemy(targetRow)) {
            return "Chosen row does not belong to the enemy.";
        }

        if (card instanceof HeartHound) {
            if (playground[Helpers.getMirrorRow(targetRow)].size() == 5) {
                return "Cannot steal enemy card since the player's row is full.";
            }
        }

        ((Environment)card).ability(targetRow);
        removeDeadMinions();
        getCurrentPlayer().getHand().getCards().remove(handIndex);
        getCurrentPlayer().setMana(Game.getCurrentPlayer().getMana() - card.getMana());

        return null;
    }

    static public void removeDeadMinions() {
        for (int i = 0; i < 4; i++) {
            ArrayList<Minion> newRow = new ArrayList<>();
            for (Minion minion : Game.playground[i]) {
                if (minion.getHealth() > 0) {
                    newRow.add(minion);
                }
            }
            Game.playground[i] = newRow;
        }
    }

    static public String minionAttackHero(int attackerRow, int attackerIndex) {
        Minion attacker = Helpers.getMinionAtPosition(attackerRow, attackerIndex);

        if (attacker == null) {
            System.out.println("Invalid cards for cardUsesAttack command");
            return null;
        }

        if (attacker.isFrozen()) {
            return "Attacker card is frozen.";
        }

        if (attacker.getHasAttacked()) {
            return "Attacker card has already attacked this turn.";
        }

        if (Helpers.enemyHasTank()) {
                return "Attacked card is not of type 'Tank'.";
        }

        attacker.attack(Game.getEnemyPlayer().getHero());

        //TODO: check if enemy hero is still alive

        return null;
    }

    static public String minionAttack(int attackerRow, int attackerIndex,
                                      int targetRow, int targetIndex) {
        Minion attacker = Helpers.getMinionAtPosition(attackerRow, attackerIndex);
        Minion target = Helpers.getMinionAtPosition(targetRow, targetIndex);

        if (attacker == null || target == null) {
            System.out.println("Invalid cards for cardUsesAttack command");
            return null;
        }

        if (!Helpers.rowBelongsToEnemy(targetRow)) {
            return "Attacked card does not belong to the enemy.";
        }

        if (attacker.getHasAttacked()) {
            return "Attacker card has already attacked this turn.";
        }

        if (attacker.isFrozen()) {
            return "Attacker card is frozen.";
        }

        if (Helpers.enemyHasTank() && !target.isTank()) {
            return "Attacked card is not of type 'Tank'.";
        }

        attacker.attack(target);
        removeDeadMinions();

        return null;
    }

    static public String minionAbility(int attackerRow, int attackerIndex,
                                      int targetRow, int targetIndex) {
        Minion attacker = Helpers.getMinionAtPosition(attackerRow, attackerIndex);
        Minion target = Helpers.getMinionAtPosition(targetRow, targetIndex);

        if (attacker == null || target == null) {
            System.out.println("Invalid cards for cardUsesAbility command");
            return null;
        }

        if (attacker.isFrozen()) {
            return "Attacker card is frozen.";
        }

        if (attacker.getHasAttacked()) {
            return "Attacker card has already attacked this turn.";
        }

        if (attacker instanceof Disciple) {
            if (Helpers.rowBelongsToEnemy(targetRow)) {
                return "Attacked card does not belong to the current player.";
            }
        } else {
            if (!Helpers.rowBelongsToEnemy(targetRow)) {
                return "Attacked card does not belong to the enemy.";
            }

            if (Helpers.enemyHasTank() && !target.isTank()) {
                return "Attacked card is not of type 'Tank'.";
            }
        }

        ((Caster)attacker).ability(target);
        removeDeadMinions();

        return null;
    }

    public static String useHeroAbility(int targetRow) {
        Hero hero = Game.getCurrentPlayer().getHero();

        if (hero.getMana() > Game.getCurrentPlayer().getMana()) {
            return "Not enough mana to use hero's ability.";
        }

        if (hero.hasAttacked()) {
            return "Hero has already attacked this turn.";
        }

        if (hero instanceof LordRoyce || hero instanceof EmpressThorina) {
            if (!Helpers.rowBelongsToEnemy(targetRow)) {
                return "Selected row does not belong to the enemy.";
            }
        } else {
            if (Helpers.rowBelongsToEnemy(targetRow)) {
                return "Selected row does not belong to the current player.";
            }
        }

        hero.ability(targetRow);
        removeDeadMinions();
        getCurrentPlayer().setMana(Game.getCurrentPlayer().getMana() - hero.getMana());

        return null;
    }

    static public void runAction(ActionsInput actionInput, ArrayNode output) {
        ObjectNode result = Helpers.mapper.createObjectNode();
        result.put("command", actionInput.getCommand());
        switch (actionInput.getCommand()) {
            case "endPlayerTurn" -> {
                Game.endTurn();
                return;
            }
            case "placeCard" -> {
                String error = placeCard(actionInput.getHandIdx());
                if (error == null) {
                    return;
                }
                result.put("handIdx", actionInput.getHandIdx());
                result.put("error", error);
            }
            case "useEnvironmentCard" -> {
                String error = useEnvironmentCard(actionInput.getHandIdx(), actionInput.getAffectedRow());
                if (error == null) {
                    return;
                }
                result.put("handIdx", actionInput.getHandIdx());
                result.put("affectedRow", actionInput.getAffectedRow());
                result.put("error", error);
            }
            case "cardUsesAttack" -> {
                String error = minionAttack(actionInput.getCardAttacker().getX(),
                        actionInput.getCardAttacker().getY(),
                        actionInput.getCardAttacked().getX(),
                        actionInput.getCardAttacked().getY());
                if (error == null) {
                    return;
                }
                result.set("cardAttacker", Helpers.coordinatesToJSON(actionInput.getCardAttacker()));
                result.set("cardAttacked", Helpers.coordinatesToJSON(actionInput.getCardAttacked()));
                result.put("error", error);
            }
            case "cardUsesAbility" -> {
                String error = minionAbility(actionInput.getCardAttacker().getX(),
                        actionInput.getCardAttacker().getY(),
                        actionInput.getCardAttacked().getX(),
                        actionInput.getCardAttacked().getY());
                if (error == null) {
                    return;
                }
                result.set("cardAttacker", Helpers.coordinatesToJSON(actionInput.getCardAttacker()));
                result.set("cardAttacked", Helpers.coordinatesToJSON(actionInput.getCardAttacked()));
                result.put("error", error);
            }
            case "useAttackHero" -> {
                String error = minionAttackHero(actionInput.getCardAttacker().getX(),
                        actionInput.getCardAttacker().getY());
                if (error == null) {
                    if (Game.getEnemyPlayer().getHero().getHealth() <= 0) {
                        ObjectNode heroDiedMessage = Helpers.mapper.createObjectNode();
                        if (currentPlayer == 1) {
                            heroDiedMessage.put("gameEnded", "Player one killed the enemy hero.");
                        } else {
                            heroDiedMessage.put("gameEnded", "Player two killed the enemy hero.");
                        }
                        output.add(heroDiedMessage);
                        Game.getCurrentPlayer().incrementWins();
                        Statistics.incrementGamesPlayed();
                    }
                    return;
                }
                result.set("cardAttacker", Helpers.coordinatesToJSON(actionInput.getCardAttacker()));
                result.put("error", error);
            }
            case "useHeroAbility" -> {
                String error = useHeroAbility(actionInput.getAffectedRow());
                if (error == null) {
                    return;
                }
                result.put("affectedRow", actionInput.getAffectedRow());
                result.put("error", error);
            }
            default -> {
                Debug.runActionDebug(actionInput, output);
                return;
            }
        }

        output.add(result);
    }
}
