package cards.heroes;

import game.Playground;

public class GeneralKocioraw extends Hero {
    @Override
    void ability(int targetRow) {
        Playground.table[targetRow].forEach(target -> {
            target.setAttackDamage(target.getAttackDamage() + 1);
        });
    }
}
