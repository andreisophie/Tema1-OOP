package cards.environments;

import cards.minions.Minion;
import game.Game;

public class Firestorm extends Environment {
    public Firestorm(final int mana,
                     final String description,
                     final String[] colors,
                     final String name) {
        super(mana, description, colors, name);
    }

    public Firestorm(final Environment environment) {
        super(environment);
    }

    /**
     * damages all minions on target row by 1 HP
     * @param targetRow casts the ability of this instance of Environment on target row
     */
    @Override
    public void ability(final int targetRow) {
        for (Minion target : Game.playground[targetRow]) {
            target.setHealth(target.getHealth() - 1);
        }
    }
}
