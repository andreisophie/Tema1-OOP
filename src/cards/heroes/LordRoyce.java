package cards.heroes;

import cards.minions.Minion;
import game.Playground;

public class LordRoyce extends Hero{
    @Override
    void ability(int targetRow) {
        Minion target = Playground.table[targetRow].get(0);

        for (Minion minion : Playground.table[targetRow]) {
            if (minion.getAttackDamage() > target.getAttackDamage()) {
                target = minion;
            }
        }

        target.setFrozen(true);
    }
}
