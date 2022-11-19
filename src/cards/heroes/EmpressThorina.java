package cards.heroes;

import cards.minions.Minion;
import game.Game;

public class EmpressThorina extends Hero {
    public EmpressThorina(final int mana,
                          final String description,
                          final String[] colors,
                          final String name) {
        super(mana, description, colors, name);
    }

    /**
     * kills card with the highest health on target row
     * @param targetRow casts this hero's ability on target row
     */
    @Override
    public void ability(final int targetRow) {
        int targetIndex = -1;
        int targetHealth = -1;
        int index = -1;

        for (Minion target : Game.playground[targetRow]) {
            index++;
            if (target.getHealth() > targetHealth) {
                targetIndex = index;
                targetHealth = target.getHealth();
            }
        }

        Game.playground[targetRow].remove(targetIndex);
        this.setHasAttacked(true);
    }
}
