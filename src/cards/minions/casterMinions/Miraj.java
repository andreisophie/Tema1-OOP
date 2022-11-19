package cards.minions.casterMinions;

import cards.Card;
import cards.minions.Caster;
import cards.minions.Minion;

public class Miraj extends Minion implements Caster {
    public Miraj(int mana,
                    String description,
                    String[] colors,
                    String name,
                    int health,
                    int attackDamage,
                    int prefRow) {
        super(mana, description, colors, name, health, attackDamage, prefRow, false );
    }

    public Miraj(Minion minion) {
        super(minion);
    }

    @Override
    public Card cloneCard() {
        Miraj clone = new Miraj(this);
        return clone;
    }

    @Override
    public void ability(Minion target) {
        int targetHealth = target.getHealth();
        target.setHealth(this.getHealth());
        this.setHealth(targetHealth);

        this.setHasAttacked(true);
    }
}
