package helpers;

import cards.Card;
import cards.environments.Environment;
import cards.environments.Firestorm;
import cards.environments.HeartHound;
import cards.environments.Winterfell;
import cards.heroes.EmpressThorina;
import cards.heroes.GeneralKocioraw;
import cards.heroes.KingMudface;
import cards.heroes.LordRoyce;
import cards.minions.Minion;
import cards.minions.casterMinions.Disciple;
import cards.minions.casterMinions.Miraj;
import cards.minions.casterMinions.TheCursedOne;
import cards.minions.casterMinions.TheRipper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.Coordinates;
import game.Game;
import game.Player;

import java.util.ArrayList;

public final class Helpers {
    private Helpers() { }

    /**
     * Receives a CardInput object and transaltes into a Card object
     * @param cardInput object to be converted
     * @return Card object to be used in game
     */
    public static Card cardInputToCard(final CardInput cardInput) {
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

    /**
     * returns cards on the playground as a JSON arrayNode of arrayNodes
     * @return
     */
    public static ArrayNode playgroundToJSON() {
        ArrayNode playgroundArray = Constants.getMapper().createArrayNode();

        for (int i = 0; i < Constants.MAX_ROW_INDEX; i++) {
            ArrayNode cardsArray = Constants.getMapper().createArrayNode();
            for (Minion minion : Game.getPlayground()[i]) {
                cardsArray.add(minion.toJSON());
            }
            playgroundArray.add(cardsArray);
        }

        return playgroundArray;
    }

    /**
     * returns environment cards currently held by a player in JSON format,
     * to be used for output
     * @param playerIndex player whose cards to be given
     * @return an ArrayNode containing environment cards held by player
     */
    public static ArrayNode environmentCardsInHand(final int playerIndex) {
        ArrayNode envArray = Constants.getMapper().createArrayNode();

        for (Card card : getPlayerByIndex(playerIndex).getHand().getCards()) {
            if (card instanceof Environment) {
                envArray.add(card.toJSON());
            }
        }

        return envArray;
    }

    /**
     * returns minion at a given position
     * @param row row where the minion is located
     * @param index column where minion is located
     * @return Minion object with request minion
     */
    public static Minion getMinionAtPosition(final int row, final int index) {
        if (Game.getPlayground()[row].size() <= index) {
            return null;
        }

        return Game.getPlayground()[row].get(index);
    }

    /**
     * returns whether given row belongs to the enemy of the player currently on their turn
     * @param targetRow index of targeted row
     * @return true if row belongs to enemy, false if not
     */
    public static boolean rowBelongsToEnemy(final int targetRow) {
        if (Game.getCurrentPlayer() == 1) {
            return targetRow == Constants.PLAYER_2_BACK_ROW
                    || targetRow == Constants.PLAYER_2_FRONT_ROW;
        } else {
            return targetRow == Constants.PLAYER_1_FRONT_ROW
                    || targetRow == Constants.PLAYER_1_BACK_ROW;
        }
    }

    /**
     * returns the mirrored row of a given row
     * @param targetRow index of targeted row
     * @return index of mirrored row
     */
    public static int getMirrorRow(final int targetRow) {
        return Constants.MAX_ROW_INDEX - targetRow;
    }

    /**
     * returns minion currently frozen on the playground in JSON format,
     * to be used for output
     * @return ArrayNode containing frozen minions
     */
    public static ArrayNode getFrozenMinions() {
        ArrayNode frozenArray = Constants.getMapper().createArrayNode();

        for (int i = 0; i < Constants.MAX_ROW_INDEX; i++) {
            for (Minion minion : Game.getPlayground()[i]) {
                if (minion.isFrozen()) {
                    frozenArray.add(minion.toJSON());
                }
            }
        }

        return frozenArray;
    }

    /**
     * unfreezes minions of player upon ending their turn
     * @param playerIndex index of player ending their turn
     */
    public static void unfreezeMinions(final int playerIndex) {
        if (playerIndex == 1) {
            for (int i = Constants.PLAYER_1_FRONT_ROW; i <= Constants.PLAYER_1_BACK_ROW; i++) {
                Game.getPlayground()[i].forEach(minion -> {
                    minion.setFrozen(false);
                    minion.setHasAttacked(false);
                });
            }
            Game.getPlayer1().getHero().setHasAttacked(false);
        }

        if (playerIndex == 2) {
            for (int i = Constants.PLAYER_2_BACK_ROW; i <= Constants.PLAYER_2_FRONT_ROW; i++) {
                Game.getPlayground()[i].forEach(minion -> {
                    minion.setFrozen(false);
                    minion.setHasAttacked(false);
                });
            }
            Game.getPlayer2().getHero().setHasAttacked(false);
        }
    }

    /**
     * checks whether enemy has a tank on the playground
     * @return true if enemy has tank, false if not
     */
    public static boolean enemyHasTank() {
        if (Game.getCurrentPlayer() == 1) {
            for (Minion minion : Game.getPlayground()[Constants.PLAYER_2_FRONT_ROW]) {
                if (minion.isTank()) {
                    return true;
                }
            }
        } else {
            for (Minion minion : Game.getPlayground()[Constants.PLAYER_1_FRONT_ROW]) {
                if (minion.isTank()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * translates a coordinates object into JSON format,
     * to be used for output
     * @param cardAttacker coordinates to be converted
     * @return ObjectNode with coordinates
     */
    public static ObjectNode coordinatesToJSON(final Coordinates cardAttacker) {
        ObjectNode coordinatesNode = Constants.getMapper().createObjectNode();
        coordinatesNode.put("x", cardAttacker.getX());
        coordinatesNode.put("y", cardAttacker.getY());
        return coordinatesNode;
    }

    /**
     * returns a player object by its index
     * @param index index of player to be returned
     * @return player with requested index
     */
    public static Player getPlayerByIndex(final int index) {
        if (index == 1) {
            return Game.getPlayer1();
        }
        return Game.getPlayer2();
    }

    /**
     *
     * @return Player object currently on their turn
     */
    public static Player getCurrentPlayer() {
        return getPlayerByIndex(Game.getCurrentPlayer());
    }

    /**
     * returns the enemy of the player currently on their turn
     * @return Player object waiting for the enemy's turn
     */
    public static Player getEnemyPlayer() {
        return getPlayerByIndex(1 + Game.getCurrentPlayer() % 2);
    }

    /**
     * returns the row where a minion should be placed
     * also considers the player currently on their turn
     * @param minion minion to check for target row
     * @return index of the row where the minion should be placed
     */
    public static int getRowForMinion(final Minion minion) {
        if (Game.getCurrentPlayer() == 1) {
            if (minion.getPrefRow() == 0) {
                return Constants.PLAYER_1_BACK_ROW;
            } else {
                return Constants.PLAYER_1_FRONT_ROW;
            }
        } else {
            if (minion.getPrefRow() == 0) {
                return Constants.PLAYER_2_BACK_ROW;
            } else {
                return Constants.PLAYER_2_FRONT_ROW;
            }
        }
    }

    /**
     * removes dead minions from the playground (with 0 or less HP)
     */
     public static void removeDeadMinions() {
        for (int i = 0; i < Constants.MAX_ROW_INDEX; i++) {
            ArrayList<Minion> newRow = new ArrayList<>();
            for (Minion minion : Game.getPlayground()[i]) {
                if (minion.getHealth() > 0) {
                    newRow.add(minion);
                }
            }
            Game.getPlayground()[i] = newRow;
        }
    }
}
