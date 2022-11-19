package cards.heroes;

import cards.minions.Minion;
import game.Game;

public class LordRoyce extends Hero {
    public LordRoyce(final int mana,
                     final String description,
                     final String[] colors,
                     final String name) {
        super(mana, description, colors, name);
    }

    /**
     * freezes minion with highest attack on target row
     * @param targetRow casts this hero's ability on target row
     */
    @Override
    public void ability(final int targetRow) {
        Minion target = Game.playground[targetRow].get(0);

        for (Minion minion : Game.playground[targetRow]) {
            if (minion.getAttackDamage() > target.getAttackDamage()) {
                target = minion;
            }
        }

        target.setFrozen(true);
        this.setHasAttacked(true);
    }
}
