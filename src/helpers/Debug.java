package helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import game.Game;

public class Debug {

    static public void runAction(ActionsInput actionInput, ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.put("command", actionInput.getCommand());
        switch (actionInput.getCommand()) {
            case "getPlayerDeck":
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.put("output", Game.getPlayerByIndex(actionInput.getPlayerIdx()).getCurrentDeck().toJSON());
                break;
            case "getPlayerHero":
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.put("output", Game.getPlayerByIndex(actionInput.getPlayerIdx()).getHero().toJSON());
                break;
            case "getCardsInHand":
                result.put("playerIdx", actionInput.getPlayerIdx());
                result.put("output", Game.getPlayerByIndex(actionInput.getPlayerIdx()).getHand().toJSON());
                break;
            case "getPlayerTurn":
                result.put("output", Game.currentPlayer);
                break;
            default:
                System.out.println("Action not recognised or implemented");
        }

        output.add(result);
    }
}
