package cards.environments;

import cards.minions.Minion;
import game.Game;

public class Firestorm extends Environment {
    public Firestorm(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void ability(int targetRow) {
        for (Minion target : Game.playground[targetRow]) {
            target.setHealth(target.getHealth() - 1);
        }
    }
}
