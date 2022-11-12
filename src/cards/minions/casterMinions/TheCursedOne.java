package cards.minions.casterMinions;

import cards.minions.Caster;
import cards.minions.Minion;

public class TheCursedOne extends Minion implements Caster {
    @Override
    public boolean checkTarget(Minion target) {
        return false;
    }

    @Override
    public void ability(Minion target) {
        int targetHealth = target.getHealth();
        target.setHealth(target.getAttackDamage());
        target.setAttackDamage(targetHealth);
        // TODO check if minion is dead

        this.setFrozen(true);
    }
}
