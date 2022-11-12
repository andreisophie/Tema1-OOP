package cards.environments;

import cards.minions.Minion;
import game.Game;

public class Winterfell extends Environment {
    public Winterfell(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    void ability(int targetRow) {
        for (Minion target : Game.playground[targetRow]) {
            target.setFrozen(true);
        }
    }
}
