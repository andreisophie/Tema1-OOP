package cards.environments;

import cards.minions.Minion;
import game.Game;
import helpers.Helpers;

public class HeartHound extends Environment {
    public HeartHound(final int mana,
                      final String description,
                      final String[] colors,
                      final String name) {
        super(mana, description, colors, name);
    }

    public HeartHound(final Environment environment) {
        super(environment);
    }

    /**
     * steals minion with the highest health from enemy
     * @param targetRow casts the ability of this instance of Environment on target row
     */
    @Override
    public void ability(final int targetRow) {
        int targetIndex = -1;
        int targetHealth = -1;
        int index = -1;

        for (Minion target : Game.getPlayground()[targetRow]) {
            index++;
            if (target.getHealth() > targetHealth) {
                targetIndex = index;
                targetHealth = target.getHealth();
            }
        }

        Minion transfer = Game.getPlayground()[targetRow].remove(targetIndex);
        Game.getPlayground()[Helpers.getMirrorRow(targetRow)].add(transfer);
    }
}
