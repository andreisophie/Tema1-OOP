package cards.environments;

import cards.minions.Minion;
import game.Playground;

public class HeartHound extends Environment {
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

        Minion transfer = Playground.table[targetRow].remove(targetIndex);
        Playground.table[3 - targetRow].add(transfer);
    }
}
