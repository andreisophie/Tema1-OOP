package cards.environments;

import cards.Card;

public abstract class Environment extends Card {
    public Environment(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    abstract void ability(int targetRow);
}
