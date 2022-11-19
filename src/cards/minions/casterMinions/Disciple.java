package cards.minions.casterMinions;

import cards.Card;
import cards.minions.Caster;
import cards.minions.Minion;

public class Disciple extends Minion implements Caster {
    public Disciple(final int mana,
                    final String description,
                    final String[] colors,
                    final String name,
                    final int health,
                    final int attackDamage,
                    final int prefRow) {
        super(mana, description, colors, name, health, attackDamage, prefRow, false);
    }

    public Disciple(final Minion minion) {
        super(minion);
    }

    /**
     *
     * @return a clone of current minion as card object
     */
    @Override
    public Card cloneMinion() {
        Disciple clone = new Disciple(this);
        return clone;
    }

    /**
     * heals target minion for 2 HP
     * @param target uses ability of current minion on target minion
     */
    @Override
    public void ability(final Minion target) {
        target.setHealth(target.getHealth() + 2);
        this.setHasAttacked(true);
    }
}
