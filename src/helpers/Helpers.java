package helpers;

import cards.Card;
import cards.environments.Environment;
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
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.*;
import game.Game;

import java.lang.reflect.Array;

public class Helpers {
    static public ObjectMapper mapper = new ObjectMapper();

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

    public static ArrayNode playgroundToJSON() {
        ArrayNode playgroundArray = mapper.createArrayNode();

        for (int i = 0; i < 4; i++) {
            ArrayNode cardsArray = mapper.createArrayNode();
            for (Minion minion : Game.playground[i]) {
                cardsArray.add(minion.toJSON());
            }
            playgroundArray.add(cardsArray);
        }

        return playgroundArray;
    }

    public static ArrayNode environmentCardsInHand(int playerIndex) {
        ArrayNode envArray = Helpers.mapper.createArrayNode();

        for (Card card : Game.getPlayerByIndex(playerIndex).getHand().getCards()) {
            if (card instanceof Environment) {
                envArray.add(card.toJSON());
            }
        }

        return envArray;
    }

    public static Minion getMinionAtPosition(int row, int index) {
        if (Game.playground[row].size() <= index) {
            return null;
        }

        return Game.playground[row].get(index);
    }

    public static boolean rowBelongsToEnemy(int targetRow) {
        if (Game.currentPlayer == 1) {
            return targetRow == 0 || targetRow == 1;
        } else {
            return targetRow == 2 || targetRow == 3;
        }
    }

    public static int getMirrorRow(int targetRow) {
        return 4 - targetRow;
    }

    public static ArrayNode getFrozenMinions() {
        ArrayNode frozenArray = mapper.createArrayNode();

        for (int i = 0; i < 4; i++) {
            for (Minion minion : Game.playground[i]) {
                if (minion.isFrozen()) {
                    frozenArray.add(minion.toJSON());
                }
            }
        }

        return frozenArray;
    }

    public static void unfreezeMinions(int playerIndex) {
        if (playerIndex == 1) {
            for (int i = 2; i < 4; i++) {
                Game.playground[i].forEach(minion -> {
                    minion.setFrozen(false);
                    minion.setHasAttacked(false);
                });
            }
        }

        if (playerIndex == 2) {
            for (int i = 0; i < 2; i++) {
                Game.playground[i].forEach(minion -> {
                    minion.setFrozen(false);
                    minion.setHasAttacked(false);
                });
            }
        }
    }

    public static boolean enemyHasTank() {
        if (Game.currentPlayer == 1) {
            for (Minion minion : Game.playground[2]) {
                if (minion.isTank())
                    return true;
            }
        } else {
            for (Minion minion : Game.playground[1]) {
                if (minion.isTank())
                    return true;
            }
        }
        return false;
    }

    public static ObjectNode coordinatesToJSON(Coordinates cardAttacker) {
        ObjectNode coordinatesNode = mapper.createObjectNode();
        coordinatesNode.put("x", cardAttacker.getX());
        coordinatesNode.put("y", cardAttacker.getY());
        return coordinatesNode;
    }
}
