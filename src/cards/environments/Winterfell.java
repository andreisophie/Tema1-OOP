package cards.environments;

import cards.Card;
import cards.minions.Minion;
import game.Game;

public class Winterfell extends Environment {
    public Winterfell(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    public Winterfell(Environment environment) {
        super(environment);
    }

    @Override
    public Card cloneCard() {
        Winterfell clone = new Winterfell(this);
        return clone;
    }

    @Override
    public void ability(int targetRow) {
        for (Minion target : Game.playground[targetRow]) {
            target.setFrozen(true);
        }
    }
}
