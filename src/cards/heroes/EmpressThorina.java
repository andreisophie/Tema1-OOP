package cards.heroes;

import cards.minions.Minion;
import game.Playground;

public class EmpressThorina extends Hero{
    @Override
    void ability(int targetRow) {
        int targetIndex = -1;
        int targetHealth = -1;
        int index = -1;

        for (Minion target : Playground.table[targetRow]) {
            index++;
            if (target.getHealth() > targetHealth) {
                targetIndex = index;
                targetHealth = target.getHealth();
            }
        }

        Playground.table[targetRow].remove(targetIndex);
    }
}
