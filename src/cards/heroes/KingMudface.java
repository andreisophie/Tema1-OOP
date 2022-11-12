package cards.heroes;

import game.Game;

public class KingMudface extends Hero {
    public KingMudface(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    void ability(int targetRow) {
        Game.playground[targetRow].forEach(target -> {
            target.setHealth(target.getHealth() + 1);
        });
    }
}
