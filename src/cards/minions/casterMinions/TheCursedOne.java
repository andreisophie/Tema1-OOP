package cards.minions.casterMinions;

import cards.Card;
import cards.minions.Caster;
import cards.minions.Minion;

public class TheCursedOne extends Minion implements Caster {
    public TheCursedOne(int mana,
                    String description,
                    String[] colors,
                    String name,
                    int health,
                    int attackDamage,
                    int prefRow) {
        super(mana, description, colors, name, health, attackDamage, prefRow, false);
    }

    public TheCursedOne(Minion minion) {
        super(minion);
    }

    @Override
    public Card cloneMinion() {
        TheCursedOne clone = new TheCursedOne(this);
        return clone;
    }

    @Override
    public void ability(Minion target) {
        int targetHealth = target.getHealth();
        target.setHealth(target.getAttackDamage());
        target.setAttackDamage(targetHealth);

        this.setHasAttacked(true);
    }
}
