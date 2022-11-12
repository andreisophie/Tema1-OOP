package cards.heroes;

import game.Playground;

public class KingMudface extends Hero {
    @Override
    void ability(int targetRow) {
        Playground.table[targetRow].forEach(target -> {
            target.setHealth(target.getHealth() + 1);
        });
    }
}
