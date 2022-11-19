package cards.minions.casterMinions;

import cards.Card;
import cards.minions.Caster;
import cards.minions.Minion;

public class Disciple extends Minion implements Caster {
    public Disciple(int mana,
                    String description,
                    String[] colors,
                    String name,
                    int health,
                    int attackDamage,
                    int prefRow) {
        super(mana, description, colors, name, health, attackDamage, prefRow, false);
    }

    public Disciple(Minion minion) {
        super(minion);
    }

    @Override
    public Card cloneMinion() {
        Disciple clone = new Disciple(this);
        return clone;
    }

    @Override
    public void ability(Minion target) {
        target.setHealth(target.getHealth() + 2);
        this.setHasAttacked(true);
    }
}
