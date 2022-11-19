package cards.minions.casterMinions;

import cards.Card;
import cards.minions.Caster;
import cards.minions.Minion;

public class TheRipper extends Minion implements Caster {
    public TheRipper(final int mana,
                     final String description,
                     final String[] colors,
                     final String name,
                     final int health,
                     final int attackDamage,
                     final int prefRow) {
        super(mana, description, colors, name, health, attackDamage, prefRow, false);
    }

    public TheRipper(final Minion minion) {
        super(minion);
    }

    /**
     *
     * @return a clone of current minion as Card object
     */
    @Override
    public Card cloneMinion() {
        TheRipper clone = new TheRipper(this);
        return clone;
    }

    /**
     * reduces attack of target minion by 2
     * @param target uses ability of current minion on target minion
     */
    @Override
    public void ability(final Minion target) {
        int targetAttack = target.getAttackDamage() - 2;
        if (targetAttack < 0) {
            targetAttack = 0;
        }
        target.setAttackDamage(targetAttack);

        this.setHasAttacked(true);
    }
}
