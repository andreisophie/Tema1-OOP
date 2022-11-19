package cards.minions.casterMinions;

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

    @Override
    public void ability(Minion target) {
        target.setHealth(target.getHealth() + 2);
        this.setHasAttacked(true);
    }
}
