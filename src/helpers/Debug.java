package helpers;

import cards.minions.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.Game;

public final class Debug {
    private Debug() { }

    /**
     * function which processes action in input, calls the corresponding helper function
     * and processes output
     * @param actionInput object containing action parameters
     * @param output object where the output is to be written
     */
    public static void runActionDebug(final ActionsInput actionInput,
                                      final ArrayNode output) {
        ObjectNode result = Constants.getMapper().createObjectNode();
        result.put("command", actionInput.getCommand());
        switch (actionInput.getCommand()) {
            case "getPlayerDeck" -> {
                final int playerIdx = actionInput.getPlayerIdx();
                result.put("playerIdx", playerIdx);
                result.set("output", Helpers.getPlayerByIndex(playerIdx).getCurrentDeck().toJSON());
            }
            case "getPlayerHero" -> {
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.set("output",
                           Helpers.getPlayerByIndex(actionInput.getPlayerIdx()).getHero().toJSON());
            }
            case "getCardsInHand" -> {
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.set("output",
                           Helpers.getPlayerByIndex(actionInput.getPlayerIdx()).getHand().toJSON());
            }
            case "getPlayerTurn" -> result.put("output", Game.getCurrentPlayer());
            case "getPlayerMana" -> {
                final int playerIdx = actionInput.getPlayerIdx();
                result.put("playerIdx", playerIdx);
                result.put("output", Helpers.getPlayerByIndex(playerIdx).getMana());
            }
            case "getCardsOnTable" -> result.set("output", Helpers.playgroundToJSON());
            case "getEnvironmentCardsInHand" -> {
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.set("output", Helpers.environmentCardsInHand(actionInput.getPlayerIdx()));
            }
            case "getCardAtPosition" -> {
                result.put("x", actionInput.getX());
                result.put("y", actionInput.getY());
                Minion minion = Helpers.getMinionAtPosition(actionInput.getX(), actionInput.getY());
                if (minion == null) {
                    result.put("output", "No card available at that position.");
                } else {
                    result.set("output", minion.toJSON());
                }
            }
            case "getFrozenCardsOnTable" -> result.set("output", Helpers.getFrozenMinions());
            default -> {
                Statistics.runActionStatistics(actionInput, output);
                return;
            }
        }

        output.add(result);
    }
}
