package cards.minions.casterMinions;

import cards.minions.Caster;
import cards.minions.Minion;

public class Disciple extends Minion implements Caster {
    @Override
    public boolean checkTarget(Minion target) {
        return false;
    }

    @Override
    public void ability(Minion target) {
        target.setHealth(target.getHealth() + 2);
        this.setFrozen(true);
    }
}
