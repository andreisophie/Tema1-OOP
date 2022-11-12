package cards.minions.casterMinions;

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
        super(mana, description, colors, name, health, attackDamage, prefRow);
    }

    @Override
    public boolean checkTarget(Minion target) {
        // TODO after I implement players, game
        return false;
    }

    @Override
    public void ability(Minion target) {
        int targetAttack = target.getAttackDamage() - 2;
        if (targetAttack < 0) {
            targetAttack = 0;
        }
        target.setAttackDamage(targetAttack);

        this.setFrozen(true);
    }
}
