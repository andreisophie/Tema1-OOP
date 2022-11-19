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
import helpers.Constants;
import helpers.Debug;
import helpers.Helpers;
import helpers.Statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class Game {
    private static ArrayList<Minion>[] playground = new ArrayList[Constants.MAX_ROW_INDEX];
    private static Player player1, player2;
    private static int currentPlayer;
    private static int startingPlayer;
    private static int turnNumber;
    private Game() { }

    public static ArrayList<Minion>[] getPlayground() {
        return playground;
    }

    public static Player getPlayer1() {
        return player1;
    }

    public static Player getPlayer2() {
        return player2;
    }

    public static int getCurrentPlayer() {
        return currentPlayer;
    }

    public static int getStartingPlayer() {
        return startingPlayer;
    }

    public static int getTurnNumber() {
        return turnNumber;
    }

    /**
     * Initializes players
     * @param inputData object which contains the data to initialize players
     */
     public static void initializePlayers(final Input inputData) {
        Deck deck;
        // initialize player 1
        player1 = new Player(inputData.getPlayerOneDecks().getNrDecks(),
                             inputData.getPlayerOneDecks().getNrCardsInDeck());
        for (int i = 0; i < player1.getNrDecks(); i++) {
            deck = new Deck();
            for (int j = 0; j < player1.getNrCardsInDeck(); j++) {
                CardInput cardInput = inputData.getPlayerOneDecks().getDecks().get(i).get(j);
                deck.getCards().add(Helpers.cardInputToCard(cardInput));
            }
            player1.addDeck(deck);
        }
        player1.setHand(new Deck());
        // initialize player 2
        player2 = new Player(inputData.getPlayerOneDecks().getNrDecks(),
                             inputData.getPlayerOneDecks().getNrCardsInDeck());
        for (int i = 0; i < player2.getNrDecks(); i++) {
            deck = new Deck();
            for (int j = 0; j < player2.getNrCardsInDeck(); j++) {
                CardInput cardInput = inputData.getPlayerTwoDecks().getDecks().get(i).get(j);
                deck.getCards().add(Helpers.cardInputToCard(cardInput));
            }
            player2.addDeck(deck);
        }
        player2.setHand(new Deck());

        Statistics.resetGamesPlayed();
    }

    /**
     * Initializes a match
     * @param startGameInput object which contains data used to initialize match
     */
    public static void initializeGame(final StartGameInput startGameInput) {
        player1.setHero((Hero) Helpers.cardInputToCard(startGameInput.getPlayerOneHero()));
        player2.setHero((Hero) Helpers.cardInputToCard(startGameInput.getPlayerTwoHero()));

        player1.setCurrentDeck(
                new Deck(player1.getDecks().get(startGameInput.getPlayerOneDeckIdx())));
        player2.setCurrentDeck(
                new Deck(player2.getDecks().get(startGameInput.getPlayerTwoDeckIdx())));

        Random random = new Random(startGameInput.getShuffleSeed());
        Collections.shuffle(player1.getCurrentDeck().getCards(), random);
        random = new Random(startGameInput.getShuffleSeed());
        Collections.shuffle(player2.getCurrentDeck().getCards(), random);

        startingPlayer = startGameInput.getStartingPlayer();
        currentPlayer = startingPlayer;
        turnNumber = 1;
        player1.setMana(1);
        player2.setMana(1);

        for (int i = 0; i < Constants.MAX_ROW_INDEX; i++) {
            playground[i] = new ArrayList<>();
        }

        player1.getHand().getCards().add(player1.getCurrentDeck().getCards().remove(0));
        player2.getHand().getCards().add(player2.getCurrentDeck().getCards().remove(0));
    }

    /**
     * refreshes variables in preparation of next match
     */
    public static void cleanupGame() {
        for (int i = 0; i < Constants.MAX_ROW_INDEX; i++) {
            playground[i].clear();
        }

        player1.getHand().getCards().clear();
        player2.getHand().getCards().clear();

        player1.getCurrentDeck().getCards().clear();
        player2.getCurrentDeck().getCards().clear();

        player1.setHero(null);
        player2.setHero(null);
    }

    /**
     * ends the turn of the current player
     * gives players mana
     * draws next card for players
     */
    public static void endTurn() {
        Helpers.unfreezeMinions(currentPlayer);

        currentPlayer = 1 + currentPlayer % 2;

        if (currentPlayer == startingPlayer) {
            turnNumber++;
            player1.setMana(player1.getMana() + (Math.min(turnNumber,
                                                          Constants.MAX_MANA_PER_TURN)));
            player2.setMana(player2.getMana() + (Math.min(turnNumber,
                                                          Constants.MAX_MANA_PER_TURN)));
            if (player1.getCurrentDeck().getCards().size() > 0) {
                player1.getHand().getCards().add(player1.getCurrentDeck().getCards().remove(0));
            }
            if (player2.getCurrentDeck().getCards().size() > 0) {
                player2.getHand().getCards().add(player2.getCurrentDeck().getCards().remove(0));
            }
        }
    }

    /**
     * checks if placing a minion is possible and places them if yes
     * @param handIndex index of the minion held by current player
     * @return an error as String if the action is illegal, null if legal
     */
    public static String placeCard(final int handIndex) {
        Card card = Helpers.getCurrentPlayer().getHand().getCards().get(handIndex);

        if (card instanceof Environment) {
            return "Cannot place environment card on table.";
        }

        if (card.getMana() > Helpers.getCurrentPlayer().getMana()) {
            return "Not enough mana to place card on table.";
        }

        if (Game.playground[Helpers.getRowForMinion((Minion) card)].size()
                == Constants.MAX_MINIONS_PER_ROW) {
            return "Cannot place card on table since row is full.";
        }

        Helpers.getCurrentPlayer().setMana(Helpers.getCurrentPlayer().getMana() - card.getMana());
        Helpers.getCurrentPlayer().getHand().getCards().remove(card);
        Game.playground[Helpers.getRowForMinion((Minion) card)].add((Minion) card);

        return null;
    }

    /**
     * wrapper for using an environment card held by the current player
     * @param handIndex index of the card in the current player's hand
     * @param targetRow the targeted row for the environment card
     * @return an error as String if the action is illegal, null if legal
     */
    public static String useEnvironmentCard(final int handIndex, final int targetRow) {
        Card card = Helpers.getCurrentPlayer().getHand().getCards().get(handIndex);

        if (!(card instanceof  Environment)) {
            return "Chosen card is not of type environment.";
        }

        if (card.getMana() > Helpers.getCurrentPlayer().getMana()) {
            return "Not enough mana to use environment card.";
        }

        if (!Helpers.rowBelongsToEnemy(targetRow)) {
            return "Chosen row does not belong to the enemy.";
        }

        if (card instanceof HeartHound) {
            if (playground[Helpers.getMirrorRow(targetRow)].size()
                    == Constants.MAX_MINIONS_PER_ROW) {
                return "Cannot steal enemy card since the player's row is full.";
            }
        }

        ((Environment) card).ability(targetRow);
        Helpers.removeDeadMinions();
        Helpers.getCurrentPlayer().getHand().getCards().remove(handIndex);
        Helpers.getCurrentPlayer().setMana(Helpers.getCurrentPlayer().getMana() - card.getMana());

        return null;
    }

    /**
     * wrapper for a minion attacking enemy hero
     * checks if action is possible and does the attack, if yes
     * @param attackerRow the row where attacker card is located
     * @param attackerIndex the column where attacker card is located
     * @return an error as String if the action is illegal, null if legal
     */
    public static String minionAttackHero(final int attackerRow, final int attackerIndex) {
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

        attacker.attack(Helpers.getEnemyPlayer().getHero());
        return null;
    }

    /**
     * wrapper for a minion attacking another minion
     * checks if action is legal and if yes, does the attack
     * @param attackerRow row of attacking minion
     * @param attackerIndex column of attacking minion
     * @param targetRow row of target minion
     * @param targetIndex column of target minion
     * @return an error as String if the action is illegal, null if legal
     */
    public static String minionAttack(final int attackerRow, final int attackerIndex,
                                      final int targetRow, final int targetIndex) {
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
        Helpers.removeDeadMinions();

        return null;
    }

    /**
     * wrapper for a minion using their ability
     * also checks if action is legal and does it if yes
     * @param attackerRow row of attacker minion
     * @param attackerIndex column of attacker minion
     * @param targetRow row of target minion
     * @param targetIndex column of target minion
     * @return an error as String if the action is illegal, null if legal
     */
    public static String minionAbility(final int attackerRow, final int attackerIndex,
                                       final int targetRow, final int targetIndex) {
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

        ((Caster) attacker).ability(target);
        Helpers.removeDeadMinions();

        return null;
    }

    /**
     * wrapper for the hero of current player using their ability
     * checks if action is legal and executes it if yes
     * @param targetRow row targeted by hero ability
     * @return an error as String if the action is illegal, null if legal
     */
    public static String useHeroAbility(final int targetRow) {
        Hero hero = Helpers.getCurrentPlayer().getHero();

        if (hero.getMana() > Helpers.getCurrentPlayer().getMana()) {
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
        Helpers.removeDeadMinions();
        Helpers.getCurrentPlayer().setMana(Helpers.getCurrentPlayer().getMana() - hero.getMana());

        return null;
    }

    /**
     * function which processes action in input, calls the corresponding wrapper function
     * and processes output
     * @param actionInput object containing action parameters
     * @param output object where the output of the action is to be placed
     */
     public static void runAction(final ActionsInput actionInput, final ArrayNode output) {
        ObjectNode result = Constants.getMapper().createObjectNode();
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
                String error = useEnvironmentCard(actionInput.getHandIdx(),
                                                  actionInput.getAffectedRow());
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
                result.set("cardAttacker",
                           Helpers.coordinatesToJSON(actionInput.getCardAttacker()));
                result.set("cardAttacked",
                           Helpers.coordinatesToJSON(actionInput.getCardAttacked()));
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
                result.set("cardAttacker",
                           Helpers.coordinatesToJSON(actionInput.getCardAttacker()));
                result.set("cardAttacked",
                           Helpers.coordinatesToJSON(actionInput.getCardAttacked()));
                result.put("error", error);
            }
            case "useAttackHero" -> {
                String error = minionAttackHero(actionInput.getCardAttacker().getX(),
                        actionInput.getCardAttacker().getY());
                if (error == null) {
                    if (Helpers.getEnemyPlayer().getHero().getHealth() <= 0) {
                        ObjectNode heroDiedMessage = Constants.getMapper().createObjectNode();
                        if (currentPlayer == 1) {
                            heroDiedMessage.put("gameEnded", "Player one killed the enemy hero.");
                        } else {
                            heroDiedMessage.put("gameEnded", "Player two killed the enemy hero.");
                        }
                        output.add(heroDiedMessage);
                        Helpers.getCurrentPlayer().incrementWins();
                        Statistics.incrementGamesPlayed();
                    }
                    return;
                }
                result.set("cardAttacker",
                           Helpers.coordinatesToJSON(actionInput.getCardAttacker()));
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
