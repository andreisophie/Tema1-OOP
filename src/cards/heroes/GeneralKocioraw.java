package cards.heroes;

import game.Game;

public class GeneralKocioraw extends Hero {
    @Override
    void ability(int targetRow) {
        Game.playground[targetRow].forEach(target -> {
            target.setAttackDamage(target.getAttackDamage() + 1);
        });
    }
}
