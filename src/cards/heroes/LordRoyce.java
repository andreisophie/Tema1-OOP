package cards.heroes;

import cards.minions.Minion;
import game.Game;

public class LordRoyce extends Hero{
    public LordRoyce(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    void ability(int targetRow) {
        Minion target = Game.playground[targetRow].get(0);

        for (Minion minion : Game.playground[targetRow]) {
            if (minion.getAttackDamage() > target.getAttackDamage()) {
                target = minion;
            }
        }

        target.setFrozen(true);
    }
}
