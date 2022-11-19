package cards.environments;

import cards.minions.Minion;
import game.Game;

public class Winterfell extends Environment {
    public Winterfell(final int mana,
                      final String description,
                      final String[] colors,
                      final String name) {
        super(mana, description, colors, name);
    }

    public Winterfell(final Environment environment) {
        super(environment);
    }

    /**
     * freezes all minion on target row
     * @param targetRow casts the ability of this instance of Environment on target row
     */
    @Override
    public void ability(final int targetRow) {
        for (Minion target : Game.playground[targetRow]) {
            target.setFrozen(true);
        }
    }
}
