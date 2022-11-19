package helpers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;

public final class Statistics {
    private static int gamesPlayed = 0;

    private Statistics() { }

    /**
     * resets the number of games played to 0
     */
    public static void resetGamesPlayed() {
        gamesPlayed = 0;
    }

    /**
     * increments the number of played games
     */
    public static void incrementGamesPlayed() {
        gamesPlayed++;
    }

    /**
     * function which processes action in input
     * and processes output
     * @param actionInput object containing action parameters
     * @param output object where the output is to be written
     */
    public static void runActionStatistics(final ActionsInput actionInput, final ArrayNode output) {
        ObjectNode result = Constants.getMapper().createObjectNode();
        result.put("command", actionInput.getCommand());
        switch (actionInput.getCommand()) {
            case "getPlayerOneWins" -> result.put("output",
                    Helpers.getPlayerByIndex(1).getNumberWins());
            case "getPlayerTwoWins" -> result.put("output",
                    Helpers.getPlayerByIndex(2).getNumberWins());
            case "getTotalGamesPlayed" -> result.put("output", gamesPlayed);
            default -> System.out.println("Action not recognised or implemented: "
                                          + actionInput.getCommand());
        }

        output.add(result);
    }
}
