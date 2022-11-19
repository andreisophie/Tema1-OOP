package cards.minions.casterMinions;

import cards.Card;
import cards.minions.Caster;
import cards.minions.Minion;

public class TheRipper extends Minion implements Caster {
    public TheRipper(int mana,
                    String description,
                    String[] colors,
                    String name,
                    int health,
                    int attackDamage,
                    int prefRow) {
        super(mana, description, colors, name, health, attackDamage, prefRow, false);
    }

    public TheRipper(Minion minion) {
        super(minion);
    }

    @Override
    public Card cloneMinion() {
        TheRipper clone = new TheRipper(this);
        return clone;
    }

    @Override
    public void ability(Minion target) {
        int targetAttack = target.getAttackDamage() - 2;
        if (targetAttack < 0) {
            targetAttack = 0;
        }
        target.setAttackDamage(targetAttack);

        this.setHasAttacked(true);
    }
}
