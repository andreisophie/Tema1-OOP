package cards.minions.casterMinions;

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
        super(mana, description, colors, name, health, attackDamage, prefRow);
    }

    @Override
    public boolean checkTarget(Minion target) {
        return false;
    }

    @Override
    public void ability(Minion target) {
        int targetHealth = target.getHealth();
        target.setHealth(this.getHealth());
        this.setHealth(targetHealth);

        this.setFrozen(true);
    }
}
