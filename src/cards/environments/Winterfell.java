package cards.environments;

import cards.minions.Minion;
import game.Playground;

public class Winterfell extends Environment {
    @Override
    void ability(int targetRow) {
        for (Minion target : Playground.table[targetRow]) {
            target.setFrozen(true);
        }
    }
}
