package game;

import cards.minions.Minion;

import java.util.ArrayList;

public class Game {
    static public ArrayList<Minion>[] playground = new ArrayList[4];
    static public Player player1, player2;
    static public int currentPlayer;

    public static Player getPlayerByIndex(int index) {
        if (index == 1)
            return player1;
        return player2;
    }
}
