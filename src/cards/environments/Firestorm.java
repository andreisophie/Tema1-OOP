package cards.environments;

import cards.minions.Minion;
import game.Game;

public class Firestorm extends Environment {
    @Override
    void ability(int targetRow) {
        for (Minion target : Game.playground[targetRow]) {
            target.setHealth(target.getHealth() - 1);
        }
    }
}
