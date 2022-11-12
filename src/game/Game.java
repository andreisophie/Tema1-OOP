package game;

import cards.minions.Minion;

import java.util.ArrayList;

public class Game {
    static public ArrayList<Minion>[] playground = new ArrayList[4];
    static private boolean initialised = false;
    static public Player player1, player2;

    public static boolean isInitialised() {
        return initialised;
    }

    public static void setInitialised(boolean initialised) {
        Game.initialised = initialised;
    }
}
