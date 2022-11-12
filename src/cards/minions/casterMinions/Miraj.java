package cards.minions.casterMinions;

import cards.minions.Caster;
import cards.minions.Minion;

public class Miraj extends Minion implements Caster {
    @Override
    public boolean checkTarget(Minion target) {
        return false;
    }

    @Override
    public void ability(Minion target) {
        int targetHealth = target.getHealth();
        target.setHealth(this.getHealth());
        this.setHealth(targetHealth);

        this.setFrozen(true);
    }
}
