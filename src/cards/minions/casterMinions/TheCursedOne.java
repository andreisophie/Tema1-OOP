package cards.minions.casterMinions;

import cards.Card;
import cards.minions.Caster;
import cards.minions.Minion;

public class TheCursedOne extends Minion implements Caster {
    public TheCursedOne(final int mana,
                        final String description,
                        final String[] colors,
                        final String name,
                        final int health,
                        final int attackDamage,
                        final int prefRow) {
        super(mana, description, colors, name, health, attackDamage, prefRow, false);
    }

    public TheCursedOne(final Minion minion) {
        super(minion);
    }

    /**
     *
     * @return a clone of current minion as Card object
     */
    @Override
    public Card cloneMinion() {
        TheCursedOne clone = new TheCursedOne(this);
        return clone;
    }

    /**
     * swaps health and attack of target minion
     * @param target uses ability of current minion on target minion
     */
    @Override
    public void ability(final Minion target) {
        int targetHealth = target.getHealth();
        target.setHealth(target.getAttackDamage());
        target.setAttackDamage(targetHealth);

        this.setHasAttacked(true);
    }
}
