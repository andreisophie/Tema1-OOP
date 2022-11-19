package helpers;

import cards.Card;
import cards.minions.Minion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.Game;
import lombok.experimental.Helper;

public class Debug {

    static public void runActionDebug(ActionsInput actionInput, ArrayNode output) {
        ObjectNode result = Helpers.mapper.createObjectNode();
        result.put("command", actionInput.getCommand());
        switch (actionInput.getCommand()) {
            case "getPlayerDeck" -> {
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.set("output", Game.getPlayerByIndex(actionInput.getPlayerIdx()).getCurrentDeck().toJSON());
            }
            case "getPlayerHero" -> {
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.set("output", Game.getPlayerByIndex(actionInput.getPlayerIdx()).getHero().toJSON());
            }
            case "getCardsInHand" -> {
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.set("output", Game.getPlayerByIndex(actionInput.getPlayerIdx()).getHand().toJSON());
            }
            case "getPlayerTurn" -> result.put("output", Game.currentPlayer);
            case "getPlayerMana" -> {
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.put("output", Game.getPlayerByIndex(actionInput.getPlayerIdx()).getMana());
            }
            case "getCardsOnTable" -> {
                result.set("output", Helpers.playgroundToJSON());
            }
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
            case "getFrozenCardsOnTable" -> {
                result.set("output", Helpers.getFrozenMinions());
            }
            default -> {
                Statistics.runActionStatistics(actionInput, output);
                return;
            }
        }

        output.add(result);
    }
}
