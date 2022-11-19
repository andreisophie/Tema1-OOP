package cards.minions.casterMinions;

import cards.Card;
import cards.minions.Caster;
import cards.minions.Minion;

public class Miraj extends Minion implements Caster {
    public Miraj(final int mana,
                 final String description,
                 final String[] colors,
                 final String name,
                 final int health,
                 final int attackDamage,
                 final int prefRow) {
        super(mana, description, colors, name, health, attackDamage, prefRow, false);
    }

    public Miraj(final
                 Minion minion) {
        super(minion);
    }

    /**
     *
     * @return clone of current minion as Card object
     */
    @Override
    public Card cloneMinion() {
        Miraj clone = new Miraj(this);
        return clone;
    }

    /**
     * swaps health of self with health of another minion
     * @param target uses ability of current minion on target minion
     */
    @Override
    public void ability(final Minion target) {
        int targetHealth = target.getHealth();
        target.setHealth(this.getHealth());
        this.setHealth(targetHealth);

        this.setHasAttacked(true);
    }
}
