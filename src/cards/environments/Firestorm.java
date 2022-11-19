package cards.environments;

import cards.Card;
import cards.minions.Minion;
import game.Game;

public class Firestorm extends Environment {
    public Firestorm(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    public Firestorm(Environment environment) {
        super(environment);
    }

    @Override
    public Card cloneCard() {
        Firestorm clone = new Firestorm(this);
        return clone;
    }

    @Override
    public void ability(int targetRow) {
        for (Minion target : Game.playground[targetRow]) {
            target.setHealth(target.getHealth() - 1);
        }
    }
}
