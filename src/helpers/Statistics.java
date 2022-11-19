package helpers;

import cards.minions.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.Game;

public class Statistics {
    static private int gamesPlayed = 0;

    static public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    static public void runActionStatistics(ActionsInput actionInput, ArrayNode output) {
        ObjectNode result = Helpers.mapper.createObjectNode();
        result.put("command", actionInput.getCommand());
        switch (actionInput.getCommand()) {
            case "getPlayerOneWins" -> {
                result.put("output", Game.getPlayerByIndex(1).getNumberWins());
            }
            case "getPlayerTwoWins" -> {
                result.put("output", Game.getPlayerByIndex(2).getNumberWins());
            }
            case "getTotalGamesPlayed" -> {
                result.put("output", gamesPlayed);
            }
            default -> System.out.println("Action not recognised or implemented: " + actionInput.getCommand());
        }

        output.add(result);
    }
}
