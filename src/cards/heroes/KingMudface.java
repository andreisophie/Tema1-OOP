package cards.heroes;

import game.Game;

public class KingMudface extends Hero {
    @Override
    void ability(int targetRow) {
        Game.playground[targetRow].forEach(target -> {
            target.setHealth(target.getHealth() + 1);
        });
    }
}
